package master.response;

public class SendCompleteReceivedResponse extends Response {
    private String masterToken;

    public SendCompleteReceivedResponse() {
    }

    public SendCompleteReceivedResponse(String masterToken) {
        this.masterToken = masterToken;
    }

    public String getMasterToken() {
        return masterToken;
    }

    public void setMasterToken(String masterToken) {
        this.masterToken = masterToken;
    }
}
