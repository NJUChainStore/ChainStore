package master.response;

public class ReceiveCompleteReceivedResponse extends Response {
    private String masterToken;

    public ReceiveCompleteReceivedResponse() {
    }

    public ReceiveCompleteReceivedResponse(String masterToken) {
        this.masterToken = masterToken;
    }

    public String getMasterToken() {
        return masterToken;
    }

    public void setMasterToken(String masterToken) {
        this.masterToken = masterToken;
    }
}
