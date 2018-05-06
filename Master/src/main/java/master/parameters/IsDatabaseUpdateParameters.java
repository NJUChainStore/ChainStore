package master.parameters;

public class IsDatabaseUpdateParameters {
    private int latestBlockIndex;

    public IsDatabaseUpdateParameters() {
    }

    public IsDatabaseUpdateParameters(int latestBlockIndex) {
        this.latestBlockIndex = latestBlockIndex;
    }

    public int getLatestBlockIndex() {
        return latestBlockIndex;
    }

    public void setLatestBlockIndex(int latestBlockIndex) {
        this.latestBlockIndex = latestBlockIndex;
    }
}
