package webservice.dataservice.account;

import trapx00.tagx00.entity.account.Role;
import trapx00.tagx00.entity.account.TempUser;
import trapx00.tagx00.entity.account.User;
import trapx00.tagx00.exception.viewexception.InvalidEmailAddressesException;
import trapx00.tagx00.exception.viewexception.SystemException;
import trapx00.tagx00.exception.viewexception.UserDoesNotExistException;

public interface UserDataService {
    /**
     * find whether the user exists
     *
     * @param username the username
     * @return whether the user exists
     */
    boolean isUserExistent(String username);

    /**
     * save the user
     *
     * @param user the user to be saved
     */
    void saveUser(User user) throws SystemException;

    /**
     * confirm the password
     *
     * @param username the username
     * @param password the password
     * @return true if password is correct else false
     */
    boolean confirmPassword(String username, String password);

    /**
     * Removes a user.
     *
     * @param username username
     */
    void deleteUser(String username);

    /**
     * send email to an user
     *
     * @param code  the validation code
     * @param email the email address
     */
    void sendEmail(String code, String email) throws InvalidEmailAddressesException;


    /**
     * save the temp user
     *
     * @param tempUser the temp user to be saved
     */
    void saveTempUser(TempUser tempUser) throws SystemException;

    /**
     * get the user's validation code by its username
     *
     * @param tempUsername the temp user's username
     * @return the validation code
     */
    TempUser getTempUserByTempUsername(String tempUsername) throws UserDoesNotExistException;

    /**
     * delete the temp user by its username
     *
     * @param tempUsername the temp user's username
     */
    void deleteTempUserByUsername(String tempUsername);

    /**
     * get user by role
     *
     * @param role
     * @return the list of users matching the role
     */
    User[] getUsersByRole(Role role);

    /**
     * get user by username
     *
     * @param username
     * @return
     */
    User getUserByUsername(String username);

    /**
     * find all of the users
     *
     * @return users
     */
    User[] findAllUsers();
}
