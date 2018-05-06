package database.response;

public class BlockAddedResponse extends Response {
    public BlockAddedResponse() {
    }

    private long blockIndex;

    public long getBlockIndex() {
        return blockIndex;
    }

    public void setBlockIndex(long blockIndex) {
        this.blockIndex = blockIndex;
    }
}
