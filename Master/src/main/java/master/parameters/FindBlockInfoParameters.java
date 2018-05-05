package master.parameters;

public class FindBlockInfoParameters {
    private int blockIndex;
    private int blockOffset;

    public FindBlockInfoParameters() {
    }

    public FindBlockInfoParameters(int blockIndex, int blockOffset) {
        this.blockIndex = blockIndex;
        this.blockOffset = blockOffset;
    }

    public int getBlockIndex() {
        return blockIndex;
    }

    public void setBlockIndex(int blockIndex) {
        this.blockIndex = blockIndex;
    }

    public int getBlockOffset() {
        return blockOffset;
    }

    public void setBlockOffset(int blockOffset) {
        this.blockOffset = blockOffset;
    }
}
