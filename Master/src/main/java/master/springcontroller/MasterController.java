package master.springcontroller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import master.blservice.MasterBlService;
import master.global.entity.Role;
import master.parameters.ReceiveCompleteParameters;
import master.parameters.SendCompleteParameters;
import master.response.ReceiveCompleteReceivedResponse;
import master.response.RegisterResponse;
import master.response.Response;
import master.response.SendCompleteReceivedResponse;
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
    @RequestMapping(value = "/register/{role}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Registered", response = RegisterResponse.class),
    })
    @ResponseBody
    public ResponseEntity<Response> register(@PathVariable("role") Role role) {
        return new ResponseEntity<>(masterBlService.register(role), HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "发送者发送结束", notes = "发送者发送结束，改发送者状态为可用")
    @RequestMapping(value = "/sendComplete", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Acknowledged", response = SendCompleteReceivedResponse.class),
            @ApiResponse(code = 403, message = "Not sender"),
    })
    @ResponseBody
    public ResponseEntity<Response> sendComplete(@RequestBody SendCompleteParameters sendCompleteParameters) {
        return new ResponseEntity<>(masterBlService.sendComplete(sendCompleteParameters.getAccessToken()), HttpStatus.OK);
    }

    @ApiOperation(value = "接受者接受结束", notes = "接受者接受结束，改接受者状态为可用")
    @RequestMapping(value = "/receivedComplete", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Acknowledged", response = ReceiveCompleteReceivedResponse.class),
            @ApiResponse(code = 403, message = "Not sender"),
    })
    @ResponseBody
    public ResponseEntity<Response> receiveComplete(@RequestBody ReceiveCompleteParameters receiveCompleteParameters) {
        return new ResponseEntity<>(masterBlService.receiveComplete(receiveCompleteParameters.getAccessToken()), HttpStatus.OK);
    }

}
