package master.request;

public class SendStartInfo {
    private long startIndex;
    private String receiverAddress;

    public SendStartInfo() {
    }

    public SendStartInfo(long startIndex, String receiverAddress) {
        this.startIndex = startIndex;
        this.receiverAddress = receiverAddress;
    }

    public long getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(long startIndex) {
        this.startIndex = startIndex;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }
}
