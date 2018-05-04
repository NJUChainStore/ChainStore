package master.parameters;

public class FindBlockInfoParameters {
    private long blockIndex;
    private long blockOffset;

    public FindBlockInfoParameters() {
    }

    public FindBlockInfoParameters(long blockIndex, long blockOffset) {
        this.blockIndex = blockIndex;
        this.blockOffset = blockOffset;
    }

    public long getBlockIndex() {
        return blockIndex;
    }

    public void setBlockIndex(long blockIndex) {
        this.blockIndex = blockIndex;
    }

    public long getBlockOffset() {
        return blockOffset;
    }

    public void setBlockOffset(long blockOffset) {
        this.blockOffset = blockOffset;
    }
}
