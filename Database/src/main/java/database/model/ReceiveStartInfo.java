package database.model;

public class ReceiveStartInfo {
    private String senderToken;

    public ReceiveStartInfo() {
    }

    public ReceiveStartInfo(String senderToken) {
        this.senderToken = senderToken;
    }

    public String getSenderToken() {
        return senderToken;
    }

    public void setSenderToken(String senderToken) {
        this.senderToken = senderToken;
    }
}
