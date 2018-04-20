package webservice.blservice.account;

import fintech100k.WebService.exception.SystemException;
import fintech100k.WebService.exception.viewexception.*;
import org.springframework.stereotype.Service;
import trapx00.tagx00.exception.viewexception.*;
import trapx00.tagx00.response.user.LevelInfoResponse;
import trapx00.tagx00.response.user.UserLoginResponse;
import trapx00.tagx00.response.user.UserRegisterConfirmationResponse;
import trapx00.tagx00.response.user.UserRegisterResponse;
import trapx00.tagx00.vo.user.UserSaveVo;

@Service
public interface UserBlService {
    /**
     * user sign up
     *
     * @param userSaveVo the user info to be saved
     * @return the register info to response
     * @throws UserAlreadyExistsException the user already exists
     * @throws SystemException            the system has error
     */
    UserRegisterResponse signUp(UserSaveVo userSaveVo) throws UserAlreadyExistsException, SystemException, InvalidEmailAddressesException;

    /**
     * confirm user's validation code
     *
     * @param token the user's token
     * @param code  the validation code
     * @return the register confirmation info to response
     */
    UserRegisterConfirmationResponse registerValidate(String token, String code) throws UserDoesNotExistException, WrongValidationCodeException, SystemException;

    /**
     * login
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return the login info to  response
     * @throws WrongUsernameOrPasswordException the username or password is error
     */
    UserLoginResponse login(String username, String password) throws WrongUsernameOrPasswordException;

    /**
     * get levels
     *
     * @return the levels array
     */
    LevelInfoResponse level();
}
