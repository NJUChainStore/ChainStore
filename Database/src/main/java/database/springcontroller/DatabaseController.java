package database.springcontroller;

import database.model.Block;
import database.model.DataReceivedResponse;
import database.model.ReceiveStartInfo;
import database.response.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DatabaseController {

    @ApiOperation(value = "增加区块", notes = "增加区块、加入队列")
    @RequestMapping(value = "/data", method = RequestMethod.POST)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Returns the hash for block", response = BlockAddedResponse.class),
        @ApiResponse(code = 403, message = "Not master"),
    })
    @ResponseBody
    public ResponseEntity<Response> addData(@RequestBody Block block) {
        return null;
    }

    @ApiOperation(value = "查找区块", notes = "根据区块号和区块偏移查找数据")
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Returns the data on the block and offset", response = BlockFoundResponse.class),
        @ApiResponse(code = 403, message = "Not master"),
    })
    @ResponseBody
    public ResponseEntity<Response> findData(@PathVariable("blockIndex") int blockIndex, @PathVariable("offset") int offset) {
        return null;
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
        return null;
    }


    @ApiOperation(value = "开始接受数据", notes = "链无效，开始接受数据")
    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Information received", response = ReceiveStartedResponse.class),
        @ApiResponse(code = 403, message = "Not master")
    })
    @ResponseBody
    public ResponseEntity<Response> startReceivingData(@RequestBody ReceiveStartInfo info) {
        return null;
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
        return null;
    }

}
