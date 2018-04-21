package master.springcontroller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import master.blservice.MasterBlService;
import master.model.*;
import master.response.ReceiveCompleteReceivedResponse;
import master.response.RegisterResponse;
import master.response.Response;
import master.response.SendCompleteReceivedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

// 主机需要开一个定时线程

@RestController
public class MasterController {

    private final MasterBlService masterBlService;

    private static Table table = new Table();

    @Autowired
    public MasterController(MasterBlService masterBlService) {
        this.masterBlService = masterBlService;
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public String test(@RequestBody TestParameters parameters) {
        System.out.println("Incoming access token: " + parameters.getAccessToken());
        if (table.getMiner() != null && table.getMiner().getAccessToken().equals(parameters.getAccessToken())) {
            System.out.println("Incoming miner request");
            return "miner request";
        }

        Optional<DatabaseItem> databaseItem = table.getDatabases().stream()
                .filter(x -> x.getAccessToken().equals(parameters.getAccessToken()))
                .findFirst();

        if (databaseItem.isPresent()) {
            System.out.println("Incoming database request. ");
            return "database request";
        } else {
            System.out.println("Invalid access token");
            return "invalid";
        }

    }

    @ApiOperation(value = "注册机器", notes = "注册存储机或者挖坑机")
    @RequestMapping(value = "/register/{role}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Registered", response = RegisterResponse.class),
    })
    @ResponseBody
    public ResponseEntity<Response> register(@PathVariable("role") Role role) {
        // 生成一个accessToken，在自己的表里增加一项为这个accessToken对应一台机器，其角色由PathVariable来决定
        // 机器向主机请求时，需要带上accessToken，主机来检查这是哪一台机器
        // 再生成一个masterToken，并发给注册者，注册者通过masterToken判断一次请求是否由主机发出
        String accessToken = UUID.randomUUID().toString(); // 生成accessToken;
        String masterToken = UUID.randomUUID().toString();
        System.out.println(String.format("Register from %s accepted. Generated accessToken: %s, masterToken: %s", role, accessToken, masterToken));

        if (role.equals(Role.MINER)) {
            table.setMiner(new MinerItem(System.currentTimeMillis(), accessToken, masterToken));
        } else {
            table.getDatabases().add(new DatabaseItem(System.currentTimeMillis(), masterToken, accessToken, DatabaseState.Available));
        }

        return new ResponseEntity<>(new RegisterResponse(accessToken, masterToken), HttpStatus.ACCEPTED);
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
