package webservice.response.user;

import trapx00.tagx00.response.Response;

public class UserRegisterResponse extends Response {
    private String token;

    public UserRegisterResponse() {
    }

    public UserRegisterResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
