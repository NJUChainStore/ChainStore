package mining.springcontroller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mining.model.Parameter;
import mining.response.MineCompleteResponse;
import mining.response.Response;
import mining.response.WrongResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;

@RestController
public class MinerController {

    @Value("${master.address}")
    private String masterAddress;


    @ApiOperation(value = "挖矿", notes = "挖矿")
    @RequestMapping(value = "/mine", method = RequestMethod.POST)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Returns the hash for block", response = MineCompleteResponse.class),
        @ApiResponse(code = 403, message = "Not master"),
    })
    @ResponseBody
    public ResponseEntity<Response> getInstanceInformation(@RequestBody Parameter parameter, HttpServletRequest request) {

        if (!request.getRemoteAddr().equals(masterAddress)) {
            return new ResponseEntity<>(new WrongResponse(403, ""), HttpStatus.FORBIDDEN);
        }

        System.out.println(request.getRemoteHost());
        System.out.println(masterAddress);

        String hash = "";
        long timestamp = System.currentTimeMillis();
        int nonce = 0;

        String target = new String(new char[parameter.getDifficulty()]).replace('\0', '0'); //创建一个用 difficulty * "0" 组成的字符串
        while(!hash.substring(0, parameter.getDifficulty()).equals(target)) {
            nonce++;
            hash = calculateHash(parameter.getPreviousHash(), timestamp, nonce, parameter.getBase64Data());
        }
        return new ResponseEntity<>(new MineCompleteResponse(
            parameter.getPreviousHash(),
            parameter.getDifficulty(),
            parameter.getBase64Data(),
            nonce,
            hash,
            timestamp
        ), HttpStatus.OK);
    }

    public String calculateHash(String previousHash, long timeStamp, int nonce, String data) {
        String calculatedhash = applySha256(
            previousHash +
                Long.toString(timeStamp) +
                Integer.toString(nonce) +
                data
        );
        return calculatedhash;
    }

    public String applySha256(String input){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            //对输入使用 sha256 算法
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte aHash : hash) {
                String hex = Integer.toHexString(0xff & aHash);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

}
