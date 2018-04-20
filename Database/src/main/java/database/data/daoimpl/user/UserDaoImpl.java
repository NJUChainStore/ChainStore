package database.data.daoimpl.user;

import fintech100k.WebService.entity.account.Role;
import fintech100k.WebService.entity.account.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trapx00.tagx00.data.dao.user.UserDao;
import trapx00.tagx00.data.fileservice.FileService;

import java.util.ArrayList;

@Service
public class UserDaoImpl implements UserDao {

    private final FileService<User> fileService;

    @Autowired
    public UserDaoImpl(FileService<User> fileService) {
        this.fileService = fileService;
    }

    @Override
    public User save(User user) {
        return fileService.saveTuple(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return fileService.findOne(username, User.class);
    }

    @Override
    public ArrayList<User> findUsersByRole(Role role) {
        return fileService.findOnes(role.getName(), User.class);
    }

    @Override
    public ArrayList<User> findAll() {
        return fileService.findAll(User.class);
    }

    @Override
    public void delete(String username) {
        fileService.delete(username, User.class);
    }
}

