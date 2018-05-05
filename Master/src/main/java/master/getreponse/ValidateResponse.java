package master.getreponse;

import master.response.Response;

public class ValidateResponse extends Response {
    private boolean result;
    private long startingInvalidBlockIndex;

    public ValidateResponse() {
    }

    public ValidateResponse(boolean result, long startingInvalidBlockIndex) {
        this.result = result;
        this.startingInvalidBlockIndex = startingInvalidBlockIndex;
    }

    public boolean getResult() {
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
