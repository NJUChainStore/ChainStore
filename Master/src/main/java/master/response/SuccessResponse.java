package master.response;

public class SuccessResponse extends Response {
    private int infoCode;
    private String description;

    public SuccessResponse(String description) {
        this.infoCode = 10000;
        this.description = description;
    }

    public int getInfoCode() {
        return infoCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
