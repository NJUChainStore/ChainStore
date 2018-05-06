package webservice.response;

import master.response.Response;

public class SaveInfoResponse extends Response {
    private long blockIndex;
    private long blockOffset;

    public SaveInfoResponse() {
    }

    public SaveInfoResponse(long blockIndex, long blockOffset) {
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
