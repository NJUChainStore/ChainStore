package master.springcontroller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import master.blservice.MasterBlService;
import master.exception.NoAvailableDatabaseException;
import master.global.entity.Role;
import master.parameters.*;
import master.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// 主机需要开一个定时线程

@RestController
public class MasterController {

    private final MasterBlService masterBlService;

    @Autowired
    public MasterController(MasterBlService masterBlService) {
        this.masterBlService = masterBlService;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public String test() {
        return "123";
    }

    @ApiOperation(value = "注册机器", notes = "注册存储机或者挖坑机")
    @RequestMapping(value = "/register/{role}", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Registered", response = RegisterResponse.class),
    })
    @ResponseBody
    public ResponseEntity<Response> register(@PathVariable("role") Role role, @RequestBody RegisterParameters registerParameters) {
        return new ResponseEntity<>(masterBlService.register(role, registerParameters.getIp()), HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "发送者发送结束", notes = "发送者发送结束，改发送者状态为可用")
    @RequestMapping(value = "/sendComplete", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Acknowledged", response = SendCompleteReceivedResponse.class),
            @ApiResponse(code = 403, message = "Not sender"),
    })
    @ResponseBody
    public ResponseEntity<Response> sendComplete(@RequestBody SendCompleteParameters sendCompleteParameters) {
        return new ResponseEntity<>(masterBlService.sendComplete(sendCompleteParameters.getAccessToken()), HttpStatus.OK);
    }

    @ApiOperation(value = "接受者接受结束", notes = "接受者接受结束，改接受者状态为可用")
    @RequestMapping(value = "/receivedComplete", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Acknowledged", response = ReceiveCompleteReceivedResponse.class),
            @ApiResponse(code = 403, message = "Not sender"),
    })
    @ResponseBody
    public ResponseEntity<Response> receiveComplete(@RequestBody ReceiveCompleteParameters receiveCompleteParameters) {
        return new ResponseEntity<>(masterBlService.receiveComplete(receiveCompleteParameters.getAccessToken()), HttpStatus.OK);
    }

    @ApiOperation(value = "矿机挖出矿", notes = "矿机挖出矿，广播给存储机")
    @RequestMapping(value = "/findBlockInfo", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Acknowledged", response = FindBlockInfoResponse.class),
            @ApiResponse(code = 403, message = "Not sender"),
            @ApiResponse(code = 503, message = "No available database")
    })
    @ResponseBody
    public ResponseEntity<Response> findBlockInfo(@RequestBody FindBlockInfoParameters findBlockInfoParameters) {
        try {
            return new ResponseEntity<>(masterBlService.findBlockInfo(findBlockInfoParameters.getBlockIndex(), findBlockInfoParameters.getBlockOffset()), HttpStatus.OK);
        } catch (NoAvailableDatabaseException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getResponse(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @ApiOperation(value = "用户保存信息", notes = "用户保存信息，存入缓冲区")
    @RequestMapping(value = "/saveInfo", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Acknowledged", response = SaveInfoResponse.class),
            @ApiResponse(code = 403, message = "Not sender"),
    })
    @ResponseBody
    public ResponseEntity<Response> saveInfo(@RequestBody SaveInfoParameters saveInfoParameters) {
        return new ResponseEntity<>(masterBlService.saveInfo(saveInfoParameters.getInfo()), HttpStatus.OK);
    }

    @ApiOperation(value = "数据机查看自己是否是最新版", notes = "数据机查看自己是否是最新版")
    @RequestMapping(value = "/isDatabaseUpdate", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Acknowledged", response = IsDatabaseUpdateResponse.class),
            @ApiResponse(code = 403, message = "Not sender"),
    })
    @ResponseBody
    public ResponseEntity<Response> isDatabaseUpdate(@RequestBody IsDatabaseUpdateParameters isDatabaseUpdateParameters) {
        return new ResponseEntity<>(masterBlService.isDatabaseUpdate(isDatabaseUpdateParameters.getLatestBlockIndex()), HttpStatus.OK);
    }
}
