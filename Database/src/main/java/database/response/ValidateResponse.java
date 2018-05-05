package database.response;

public class ValidateResponse extends Response {
    private boolean result;
    private long startingInvalidBlockIndex;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public long getStartingInvalidBlockIndex() {
        return startingInvalidBlockIndex;
    }

    public void setStartingInvalidBlockIndex(long startingInvalidBlockIndex) {
        this.startingInvalidBlockIndex = startingInvalidBlockIndex;
    }
}
