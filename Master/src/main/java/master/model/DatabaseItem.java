package master.model;

public class DatabaseItem {
    private long registerTimestamp;
    private String masterToken;
    private String accessToken;
    private DatabaseState state;

    public DatabaseItem(long registerTimestamp, String masterToken, String accessToken, DatabaseState state) {
        this.registerTimestamp = registerTimestamp;
        this.masterToken = masterToken;
        this.accessToken = accessToken;
        this.state = state;
    }

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

    public DatabaseState getState() {
        return state;
    }

    public void setState(DatabaseState state) {
        this.state = state;
    }
}
