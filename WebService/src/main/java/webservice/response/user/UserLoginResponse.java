package webservice.response.user;

import fintech100k.WebService.security.jwt.JwtRole;
import trapx00.tagx00.response.Response;

import java.util.Collection;

public class UserLoginResponse extends Response {
    private String token;
    private Collection<JwtRole> jwtRoles;
    private String email;

    public UserLoginResponse() {
    }

    public UserLoginResponse(String token, Collection<JwtRole> jwtRoles, String email) {
        this.token = token;
        this.jwtRoles = jwtRoles;
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Collection<JwtRole> getJwtRoles() {
        return jwtRoles;
    }

    public void setJwtRoles(Collection<JwtRole> jwtRoles) {
        this.jwtRoles = jwtRoles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
