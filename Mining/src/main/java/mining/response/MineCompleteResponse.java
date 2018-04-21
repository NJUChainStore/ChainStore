package mining.response;

public class MineCompleteResponse extends Response {
    private String previousHash;
    private int difficulty;
    private String base64Data;
    private int nonce;
    private String hash;
    private long timestamp;



    public MineCompleteResponse(String previousHash, int difficulty, String base64Data, int nonce, String hash, long timestamp) {
        this.previousHash = previousHash;
        this.difficulty = difficulty;
        this.base64Data = base64Data;
        this.nonce = nonce;
        this.hash = hash;
        this.timestamp = timestamp;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getBase64Data() {
        return base64Data;
    }

    public void setBase64Data(String base64Data) {
        this.base64Data = base64Data;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
