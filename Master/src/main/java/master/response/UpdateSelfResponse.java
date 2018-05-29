package master.response;

public class UpdateSelfResponse extends Response {
    private String info;

    public UpdateSelfResponse() {
    }

    public UpdateSelfResponse(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
