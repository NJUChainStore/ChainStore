package master.response;

public class RegisterResponse extends Response {
    private String accessToken; // 矿机每次要向主机发请求时，需要带上这个token，让主机知道是哪个机器发出的请求
    private String masterToken; // 矿机根据这个token，来判断一次request是否为主机发出

    public RegisterResponse() {
    }

    public RegisterResponse(String accessToken, String masterToken) {
        this.accessToken = accessToken;
        this.masterToken = masterToken;
    }

    public String getMasterToken() {
        return masterToken;
    }

    public void setMasterToken(String masterToken) {
        this.masterToken = masterToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
