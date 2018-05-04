package master.response;

public class FindBlockInfoResponse extends Response {
    String data;

    public FindBlockInfoResponse() {
    }

    public FindBlockInfoResponse(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
