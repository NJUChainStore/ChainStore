package database.response;

import database.model.Block;

public class BlockFoundResponse extends Response {
    public BlockFoundResponse() {
    }

    private String base64Data;

    public void setBase64Data(String base64Data) {
        this.base64Data = base64Data;
    }
}
