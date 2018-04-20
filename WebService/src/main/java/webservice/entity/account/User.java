package webservice.entity.account;


import trapx00.tagx00.entity.Entity;
import trapx00.tagx00.entity.annotation.Column;
import trapx00.tagx00.entity.annotation.ElementCollection;
import trapx00.tagx00.entity.annotation.Id;
import trapx00.tagx00.entity.annotation.Table;

import java.util.List;

@Table(name = "user")
public class User extends Entity {
    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @ElementCollection(targetClass = Role.class)
    @Column(name = "roles")
    private List<Role> roles;
    @Column(name = "exp")
    private double exp;
    @Column(name = "credits")
    private int credits;

    public User() {
    }

    public User(String username, String password, String email, List<Role> roles, double exp, int credits) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
        this.exp = exp;
        this.credits = credits;
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

    public double getExp() {
        return exp;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}
