package webservice.vo.user;

import fintech100k.WebService.entity.account.Role;

import java.io.Serializable;
import java.util.List;

public class UserSaveVo implements Serializable {
    private String username;
    private String password;
    private String email;
    private List<Role> roles;

    public UserSaveVo() {
    }

    public UserSaveVo(String username, String password, String email, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
