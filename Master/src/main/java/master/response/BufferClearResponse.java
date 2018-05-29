package master.response;

public class BufferClearResponse extends Response {
    private String info;

    public BufferClearResponse() {
    }

    public BufferClearResponse(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
