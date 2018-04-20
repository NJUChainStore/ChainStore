package webservice.springcontroller.account;

import fintech100k.WebService.blservice.account.UserBlService;
import fintech100k.WebService.entity.account.Role;
import fintech100k.WebService.exception.SystemException;
import fintech100k.WebService.exception.viewexception.*;
import fintech100k.WebService.response.Response;
import fintech100k.WebService.response.WrongResponse;
import fintech100k.WebService.response.user.UserLoginResponse;
import fintech100k.WebService.response.user.UserRegisterResponse;
import fintech100k.WebService.vo.user.UserSaveVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import trapx00.tagx00.exception.viewexception.*;

import java.util.ArrayList;

@RestController
public class UserController {
    private final UserBlService userBlService;

    @Autowired
    public UserController(UserBlService userBlService) {
        this.userBlService = userBlService;
    }

    @RequestMapping(value = "account/try", method = RequestMethod.GET)
    @ResponseBody
    public String trial(@RequestParam("username") String username) {
        return (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    }

    @ApiOperation(value = "用户登录", notes = "验证用户登录并返回token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
    })
    @RequestMapping(value = "account/login", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserLoginResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> login(
            @RequestParam("username") String username, @RequestParam("password") String password) {
        try {
            UserLoginResponse userLoginResponse = userBlService.login(username, password);
            return new ResponseEntity<>(userLoginResponse, HttpStatus.OK);
        } catch (WrongUsernameOrPasswordException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getResponse(), HttpStatus.UNAUTHORIZED);
        }
    }


    @ApiOperation(value = "用户注册", notes = "验证是否存在同样的用户名，帮助用户注册，并返回token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "email", value = "电子邮箱地址", required = true, dataType = "String"),
            @ApiImplicitParam(name = "role", value = "注册的角色", required = true, dataType = "Role")
    })
    @RequestMapping(method = RequestMethod.POST, path = "account/register", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success", response = UserRegisterResponse.class),
            @ApiResponse(code = 400, message = "invalid email address", response = WrongResponse.class),
            @ApiResponse(code = 409, message = "Conflict", response = WrongResponse.class),
            @ApiResponse(code = 503, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> register(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("email") String email, @RequestParam("role") Role role) {
        try {
            ArrayList<Role> roles = new ArrayList<>();
            roles.add(role);
            return new ResponseEntity<>(userBlService.signUp(new UserSaveVo(username, password, email, roles)), HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getResponse(), HttpStatus.CONFLICT);
        } catch (SystemException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getResponse(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (InvalidEmailAddressesException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getResponse(), HttpStatus.BAD_REQUEST);
        }
    }
}
