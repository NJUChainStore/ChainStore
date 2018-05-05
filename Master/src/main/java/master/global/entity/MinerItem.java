package master.global.entity;

public class MinerItem {
    private long registerTimestamp;
    private String accessToken;
    private String masterToken;
    private String ip;

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public MinerItem() {
    }

    public MinerItem(long registerTimestamp, String accessToken, String masterToken, String ip) {
        this.registerTimestamp = registerTimestamp;
        this.accessToken = accessToken;
        this.masterToken = masterToken;
        this.ip = ip;
    }
}
