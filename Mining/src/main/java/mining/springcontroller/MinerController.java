package mining.springcontroller;

import io.swagger.annotations.*;
import mining.model.Parameter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MinerController {

    @Value("${masterAddress}")
    private String masterAddress;


    @ApiOperation(value = "挖矿", notes = "挖矿")
    @RequestMapping(value = "/mine", method = RequestMethod.POST)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Returns the hash for block", response = String.class),
        @ApiResponse(code = 403, message = "Not master"),
    })
    @ResponseBody
    public ResponseEntity<String> getInstanceInformation(@RequestBody Parameter parameter) {
        return null;
    }


}
