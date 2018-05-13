package database.response;

public class BlockFoundResponse extends Response {
    public BlockFoundResponse() {
    }

    public BlockFoundResponse(String base64Data) {
        this.base64Data = base64Data;
    }

    private String base64Data;

    public void setBase64Data(String base64Data) {
        this.base64Data = base64Data;
    }

    public String getBase64Data() {
        return base64Data;
    }
}
