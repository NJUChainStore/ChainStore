package database.model;

import java.util.ArrayList;

public class Block {
    private long index;
    private String previousHash;
    private String hash;
    private long timestamp;
    private int nounce;
    private ArrayList<String> base64Data;

    public void setIndex(long index) {
        this.index = index;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setNounce(int nounce) {
        this.nounce = nounce;
    }

    public void setBase64Data(ArrayList<String> base64Data) {
        this.base64Data = base64Data;
    }

    public long getIndex() {
        return index;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getHash() {
        return hash;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getNounce() {
        return nounce;
    }


    public ArrayList<String> getBase64Data() {
        return base64Data;
    }
}
