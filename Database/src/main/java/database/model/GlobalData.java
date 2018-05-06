package database.model;

public class GlobalData {
  private  State state=State.VALID;
  private  int latestBlockIndex=0;
  //private  String myIP;
  private  String masterIP="localhost:8080";
  private  String masterToken;
  private  String accessToken;
  private static GlobalData globalData=new GlobalData();
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getLatestBlockIndex() {
        return latestBlockIndex;
    }

    public void setLatestBlockIndex(int latestBlockIndex) {
        this.latestBlockIndex = latestBlockIndex;
    }

    public String getMasterIP() {
        return masterIP;
    }

    public void setMasterIP(String masterIP) {
        this.masterIP = masterIP;
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

    public static GlobalData getInstance(){
        return globalData;
    }
}
