package master.parameters;

public class ReceiveCompleteParameters {
    private String accessToken;

    public ReceiveCompleteParameters() {
    }

    public ReceiveCompleteParameters(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
