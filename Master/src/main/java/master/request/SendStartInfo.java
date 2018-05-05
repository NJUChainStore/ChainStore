package master.request;

public class SendStartInfo {
    private String receiverAddress;

    public SendStartInfo() {
    }

    public SendStartInfo(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }
}
