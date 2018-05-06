package database.response;

import database.response.Response;

public class DataReceivedResponse extends Response {
     private int latestIndex;

    public DataReceivedResponse() {
    }

    public void setLatestIndex(int latestIndex) {
        this.latestIndex = latestIndex;
    }
}
