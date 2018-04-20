package webservice.data.account;

import fintech100k.WebService.dataservice.account.UserDataService;
import fintech100k.WebService.entity.account.Role;
import fintech100k.WebService.entity.account.User;
import fintech100k.WebService.exception.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import trapx00.tagx00.data.dao.user.TempUserDao;
import trapx00.tagx00.data.dao.user.UserDao;
import trapx00.tagx00.entity.account.TempUser;
import trapx00.tagx00.exception.viewexception.InvalidEmailAddressesException;
import trapx00.tagx00.exception.viewexception.UserDoesNotExistException;

import java.util.ArrayList;

@Service
public class UserDataServiceImpl implements UserDataService {
    private final UserDao userDao;
    private final TempUserDao tempUserDao;
    private final JavaMailSender mailSender;

    @Value("${email.sender}")
    private String senderEmail;
    @Value("${email.subject}")
    private String subject;
    @Value("${email.content1}")
    private String content1;
    @Value("${email.content2}")
    private String content2;

    @Autowired
    public UserDataServiceImpl(UserDao userDao, TempUserDao tempUserDao, JavaMailSender mailSender) {
        this.userDao = userDao;
        this.tempUserDao = tempUserDao;
        this.mailSender = mailSender;
    }


    /**
     * find whether the user exists
     *
     * @param username the username
     * @return whether the user exists
     */
    @Override
    public boolean isUserExistent(String username) {
        return userDao.findUserByUsername(username) != null;
    }

    /**
     * save the user
     *
     * @param user the user to be saved
     */
    @Override
    public void saveUser(User user) throws SystemException {
        if (userDao.save(user) == null) {
            throw new SystemException();
        }
    }

    /**
     * confirm the password
     *
     * @param username the username
     * @param password the password
     * @return true if password is correct else false
     */
    @Override
    public boolean confirmPassword(String username, String password) {
        User user = userDao.findUserByUsername(username);
        if (user != null) {
            if (BCrypt.checkpw(password, user.getPassword())) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * Removes a user. No exception is thrown if username doesn't exist.
     *
     * @param username username
     */
    @Override
    public void deleteUser(String username) {
        userDao.delete(username);
    }

    /**
     * send email to an user
     *
     * @param code  the validation code
     * @param email the email address
     */
    @Override
    public void sendEmail(String code, String email) throws InvalidEmailAddressesException {
        SimpleMailMessage message = new SimpleMailMessage();
        String content = content1 + code + content2;
        message.setFrom(senderEmail);
        message.setTo(email);
        message.setSubject(subject);
        message.setText(content);

        try {
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidEmailAddressesException();
        }
    }

    /**
     * save the temp user
     *
     * @param tempUser the temp user to be saved
     */
    @Override
    public void saveTempUser(TempUser tempUser) throws SystemException {
        if (tempUserDao.save(tempUser) == null) {
            throw new SystemException();
        }
    }

    /**
     * get the user's validation code by its username
     *
     * @param tempUsername the temp user's username
     * @return the validation code
     */
    @Override
    public TempUser getTempUserByTempUsername(String tempUsername) throws UserDoesNotExistException {
        TempUser tempUser = tempUserDao.findTempUserByUsername(tempUsername);
        if (tempUser == null) {
            throw new UserDoesNotExistException();
        } else {
            return tempUser;
        }
    }

    /**
     * delete the temp user by its username
     *
     * @param tempUsername the temp user's username
     */
    @Override
    public void deleteTempUserByUsername(String tempUsername) {
        tempUserDao.delete(tempUsername);
    }

    /**
     * get user by role
     *
     * @param role
     * @return the list of users matching the role
     */
    @Override
    public User[] getUsersByRole(Role role) {
        ArrayList<User> userArrayList = userDao.findUsersByRole(role);
        return userArrayList.toArray(new User[userArrayList.size()]);
    }

    /**
     * get user by username
     *
     * @param username
     * @return
     */
    @Override
    public User getUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    /**
     * find all of the users
     *
     * @return users
     */
    @Override
    public User[] findAllUsers() {
        ArrayList<User> userArrayList = userDao.findAll();
        return userArrayList.toArray(new User[userArrayList.size()]);
    }
}
