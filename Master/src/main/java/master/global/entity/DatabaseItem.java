package master.global.entity;

import java.util.Date;

public class DatabaseItem {
    private long registerTimestamp;
    private String masterToken;
    private String accessToken;
    private DatabaseState state;
    private Date lastValidateTime;
    private String ip;

    public DatabaseItem(long registerTimestamp, String masterToken, String accessToken, DatabaseState state, Date lastValidateTime, String ip) {
        this.registerTimestamp = registerTimestamp;
        this.masterToken = masterToken;
        this.accessToken = accessToken;
        this.state = state;
        this.lastValidateTime = lastValidateTime;
        this.ip = ip;
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

    public Date getLastValidateTime() {
        return lastValidateTime;
    }

    public void setLastValidateTime(Date lastValidateTime) {
        this.lastValidateTime = lastValidateTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
