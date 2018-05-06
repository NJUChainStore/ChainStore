package master.parameters;

public class TestParameters {
    private String accessToken;

    public TestParameters() {
    }

    public TestParameters(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
