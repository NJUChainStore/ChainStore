package master.model;

public class MinerItem {
    private long registerTimestamp;
    private String accessToken;
    private String masterToken;


    public long getRegisterTimestamp() {
        return registerTimestamp;
    }

    public void setRegisterTimestamp(long registerTimestamp) {
        this.registerTimestamp = registerTimestamp;
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

    public MinerItem(long registerTimestamp, String accessToken, String masterToken) {
        this.registerTimestamp = registerTimestamp;
        this.accessToken = accessToken;
        this.masterToken = masterToken;
    }
}
