package master.response;

public class WrongResponse extends Response {
    private int infoCode;
    private String description;

    public WrongResponse(int infoCode, String description) {
        this.infoCode = infoCode;
        this.description = description;
    }

    public int getInfoCode() {
        return infoCode;
    }

    public void setInfoCode(int infoCode) {
        this.infoCode = infoCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
