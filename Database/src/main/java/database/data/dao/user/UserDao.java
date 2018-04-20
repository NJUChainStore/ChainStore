package database.data.dao.user;


import fintech100k.WebService.entity.account.Role;
import fintech100k.WebService.entity.account.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public interface UserDao {
    User save(User user);

    User findUserByUsername(String username);

    ArrayList<User> findUsersByRole(Role role);

    ArrayList<User> findAll();

    void delete(String username);
}
