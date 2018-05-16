package database.model;

public class SendStartInfo {
    private int startIndex;
    private String receiverAddress;

    public SendStartInfo() {
    }

    public SendStartInfo(int startIndex, String receiverAddress) {
        this.startIndex = startIndex;
        this.receiverAddress = receiverAddress;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }
}
