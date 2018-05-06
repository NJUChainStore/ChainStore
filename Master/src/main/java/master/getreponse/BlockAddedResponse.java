package master.getreponse;

import master.response.Response;

public class BlockAddedResponse extends Response {
    private long blockIndex;

    public BlockAddedResponse() {
    }

    public BlockAddedResponse(long blockIndex) {
        this.blockIndex = blockIndex;
    }

    public long getBlockIndex() {
        return blockIndex;
    }

    public void setBlockIndex(long blockIndex) {
        this.blockIndex = blockIndex;
    }
}
