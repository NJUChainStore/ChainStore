package database.springcontroller;

import database.cache.Cache;
import database.data.dao.user.BlockDao;
import database.data.daoimpl.user.BlockDaoImpl;
import database.model.*;
import database.response.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class DatabaseController {

    @Autowired
    BlockDao blockDao;
    
    @Autowired
    Cache cache;

    GlobalData globalData = GlobalData.getInstance();

    String tempMasterIp="";

    @Value("${url.server}")
    private static String masterUrl;

    @ApiOperation(value = "增加区块", notes = "增加区块、加入队列")
    @RequestMapping(value = "/data", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns the hash for block", response = BlockAddedResponse.class),
            @ApiResponse(code = 403, message = "Not master"),
    })
    @ResponseBody
    public ResponseEntity<Response> addData(@RequestBody Block block) {

        cache.addBlock(block);
        if (globalData.getState() == State.VALID) {
            setMaxIndex();
            cache.writeAllBlocks();
        }
        BlockAddedResponse blockAddedResponse = new BlockAddedResponse();
        blockAddedResponse.setBlockIndex(globalData.getLatestBlockIndex());
        return new ResponseEntity<>(blockAddedResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "查找区块", notes = "根据区块号和区块偏移查找数据")
    @RequestMapping(value = "/data/{blockIndex}/{offset}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns the data on the block and offset", response = BlockFoundResponse.class),
            @ApiResponse(code = 403, message = "Not master"),
    })
    @ResponseBody
    public ResponseEntity<Response> findData(@PathVariable("blockIndex") int blockIndex, @PathVariable("offset") int offset) {
        String info = blockDao.getSingleRecord(blockIndex, offset);
        BlockFoundResponse blockFoundResponse = new BlockFoundResponse();
        blockFoundResponse.setBase64Data(info);
        return new ResponseEntity<>(blockFoundResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "验证链正确性", notes = "验证链正确性")
    @RequestMapping(value = "/validate", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Validate complete", response = ValidateResponse.class),
            @ApiResponse(code = 403, message = "Not master")
    })
    @ResponseBody
    public ResponseEntity<Response> validate() {
        // 进入方法改自己状态为正在验证
        // 验证成功，改自己状态为可用
        // 不成功，改自己状态为无效，并记住第一个无效块的索引
        globalData.setState(State.CHECKING);

        int res;
        if (globalData.getLatestBlockIndex() >= 0) {
            res = blockDao.check(globalData.getLatestBlockIndex());
        } else {
            if(cache.findMaxIndex()>0){
                setMaxIndex();
                cache.writeAllBlocks();
                res=blockDao.check(globalData.getLatestBlockIndex());
            }else{
                res = -1;
                System.out.println("没有收到过任何区块！");
            }

        }
        ValidateResponse validateResponse = new ValidateResponse();

        if (res == -1) {
            validateResponse.setResult(true);
            validateResponse.setStartingInvalidBlockIndex(-1);
            globalData.setState(State.VALID);
        } else {
            validateResponse.setResult(false);
            //清空缓存
            cache.removeAll();
            validateResponse.setStartingInvalidBlockIndex(res);
            globalData.setState(State.INVALID);
        }
        return new ResponseEntity<>(validateResponse, HttpStatus.OK);
    }


    @ApiOperation(value = "开始接受数据", notes = "链无效，开始接受数据")
    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Information received", response = ReceiveStartedResponse.class),
            @ApiResponse(code = 403, message = "Not master")
    })
    @ResponseBody
    public ResponseEntity<Response> startReceivingData(@RequestBody ReceiveStartInfo info) {
        globalData.setState(State.RECEIVING);
        tempMasterIp=globalData.getMasterToken();
        globalData.setMasterToken(info.getSenderToken());
        return new ResponseEntity<>(new ReceiveStartedResponse(), HttpStatus.OK);
    }

    @ApiOperation(value = "接受数据", notes = "无效状态下，接受数据")
    @RequestMapping(value = "/receive", method = RequestMethod.PATCH)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Data received", response = DataReceivedResponse.class),
            @ApiResponse(code = 403, message = "Not designated sender")
    })
    @ResponseBody
    public ResponseEntity<Response> receiveData(@RequestBody Block[] blocks) {
        // 假设一次request能接受所有数据
        // 接受结束后，把缓存里的数据加入自己的链
        // 再在返回response之前，通知主机自己接受数据完毕
        //cache.removeAll();
        for (Block block : blocks) {
            cache.addBlock(block);
        }
        setMaxIndex();
        DataReceivedResponse dataReceivedResponse = new DataReceivedResponse();
        dataReceivedResponse.setLatestIndex(cache.findMaxIndex());
        cache.writeAllBlocks();
        if (isLatest() == true) {
            globalData.setState(State.VALID);
        } else {
            globalData.setState(State.INVALID);
        }
        globalData.setMasterToken(tempMasterIp);
        return new ResponseEntity<>(dataReceivedResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "开始发送数据", notes = "给链无效的机器发送数据")
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Information received", response = ReceiveStartedResponse.class),
            @ApiResponse(code = 403, message = "Not master")
    })
    @ResponseBody
    public ResponseEntity<Response> startSendingData(@RequestBody SendStartInfo sendStartInfo) {
        if(cache.findMaxIndex()>0) {
            setMaxIndex();
            cache.writeAllBlocks();
        }
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("authentication", globalData.getAccessToken());
       // HttpEntity<IsDatabaseUpdateParameters> entity = new HttpEntity<>(new IsDatabaseUpdateParameters(globalData.getLatestBlockIndex()), headers);
        ArrayList<Block> blocks =blockDao.readBlocks(sendStartInfo.getStartIndex(),globalData.getLatestBlockIndex());
       // blockDao.
        String url = sendStartInfo.getReceiverAddress()+"/receive";
        HttpEntity<ArrayList<Block>> entity = new HttpEntity<>(blocks, headers);
        DataReceivedResponse response = restTemplate.postForEntity(url, entity, DataReceivedResponse.class).getBody();

        // notify master
        restTemplate.postForEntity(masterUrl+"sendComplete",
                new HttpEntity<>(new SendCompleteParameters(globalData.getAccessToken()), headers)
        , Object.class);



        if(cache.findMaxIndex()>0) {
            setMaxIndex();
            cache.writeAllBlocks();
        }
        return new ResponseEntity<>(new Response(), HttpStatus.OK);
    }

    private void setMaxIndex() {
        int max = -1;
        max = cache.findMaxIndex();
        if (max != -1)
            globalData.setLatestBlockIndex(max);
    }

    private boolean isLatest() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<IsDatabaseUpdateParameters> entity = new HttpEntity<>(new IsDatabaseUpdateParameters(globalData.getLatestBlockIndex()), headers);
        String url = masterUrl+"/isDatabaseUpdate";
        IsDatabaseUpdateResponse isDatabaseUpdateResponse = restTemplate.exchange(url, HttpMethod.POST, entity, IsDatabaseUpdateResponse.class).getBody();
        return isDatabaseUpdateResponse.isUpdate();
    }

}
