package mining.springcontroller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mining.parameters.MineParameter;
import mining.response.MineCompleteResponse;
import mining.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
public class MinerController {

    @ApiOperation(value = "挖矿", notes = "挖矿")
    @RequestMapping(value = "/mine", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns the hash for block", response = MineCompleteResponse.class),
            @ApiResponse(code = 403, message = "Not master"),
    })
    @ResponseBody
    public ResponseEntity<Response> mine(@RequestBody MineParameter mineParameter) {

        String hash = "";
        long timestamp = System.currentTimeMillis();
        int nonce = 0;

        String target = new String(new char[mineParameter.getDifficulty()]).replace('\0', '0'); //创建一个用 difficulty * "0" 组成的字符串
        while (!hash.substring(0, mineParameter.getDifficulty()).equals(target)) {
            nonce++;
            hash = calculateHash(mineParameter.getPreviousHash(), timestamp, nonce, mineParameter.getBase64Data());
        }
        return new ResponseEntity<>(new MineCompleteResponse(
                mineParameter.getPreviousHash(),
                mineParameter.getDifficulty(),
                mineParameter.getBase64Data(),
                nonce,
                hash,
                timestamp
        ), HttpStatus.OK);
    }

    public String calculateHash(String previousHash, long timeStamp, int nonce, ArrayList<String> data) {
        String calculatedhash = applySha256(
                previousHash +
                        Long.toString(timeStamp) +
                        Integer.toString(nonce) +
                        data.stream().collect(Collectors.joining("/"))
        );
        return calculatedhash;
    }

    public String applySha256(String input) {
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
