package master.springcontroller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import master.response.ReceiveCompleteReceivedResponse;
import master.response.Response;
import master.response.SendCompleteReceivedResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

// 主机需要开一个定时线程

@RestController
public class MasterController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test(HttpServletRequest request) {
        System.out.println(request.getRemoteAddr() + ":" + request.getRemotePort());
    }

    @ApiOperation(value = "发送者发送结束", notes = "发送者发送结束，改发送者状态为可用")
    @RequestMapping(value = "/sendComplete", method = RequestMethod.GET)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Acknowledged", response = SendCompleteReceivedResponse.class),
        @ApiResponse(code = 403, message = "Not sender"),
    })
    @ResponseBody
    public ResponseEntity<Response> sendComplete() {
        return null;
    }

    @ApiOperation(value = "接受者接受结束", notes = "接受者接受结束，改接受者状态为可用")
    @RequestMapping(value = "/receivedComplete", method = RequestMethod.GET)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Acknowledged", response = ReceiveCompleteReceivedResponse.class),
        @ApiResponse(code = 403, message = "Not sender"),
    })
    @ResponseBody
    public ResponseEntity<Response> receiveComplete() {
        return null;
    }

}
