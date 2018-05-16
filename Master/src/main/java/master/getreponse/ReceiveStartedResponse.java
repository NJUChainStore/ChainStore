package master.getreponse;

import master.response.Response;

public class ReceiveStartedResponse extends Response {
    private String haha;

    public ReceiveStartedResponse() {
    }

    public ReceiveStartedResponse(String haha) {
        this.haha = haha;
    }

    public String getHaha() {
        return haha;
    }

    public void setHaha(String haha) {
        this.haha = haha;
    }
}
