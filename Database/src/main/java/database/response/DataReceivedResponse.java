package database.response;

public class DataReceivedResponse extends Response {
    private int latestIndex;
    public DataReceivedResponse() {
    }

    public int getLatestIndex() {
        return latestIndex;
    }
    public void setLatestIndex(int latestIndex) {
        this.latestIndex = latestIndex;
    }
}
