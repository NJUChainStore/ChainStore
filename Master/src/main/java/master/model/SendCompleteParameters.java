package master.model;

public class SendCompleteParameters {
    private String accessToken;

    public SendCompleteParameters() {
    }

    public SendCompleteParameters(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
