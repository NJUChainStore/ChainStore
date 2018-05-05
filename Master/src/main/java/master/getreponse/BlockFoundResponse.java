package master.getreponse;

import master.response.Response;

public class BlockFoundResponse extends Response {
    private String base64Data;

    public BlockFoundResponse() {
    }

    public String getBase64Data() {
        return base64Data;
    }

    public void setBase64Data(String base64Data) {
        this.base64Data = base64Data;
    }
}
