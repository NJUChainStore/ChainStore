package database.model;

import java.util.ArrayList;

public class Block {
    private long index;
    private String previousHash;
    private String hash;
    private long timestamp;
    private int nonce;
    private int difficulty;
    private ArrayList<String> base64Data;

    public Block() {
    }

    public Block(long index, String previousHash, String hash, long timestamp, int nonce, int difficulty, ArrayList<String> base64Data) {
        this.index = index;
        this.previousHash = previousHash;
        this.hash = hash;
        this.timestamp = timestamp;
        this.nonce = nonce;
        this.difficulty = difficulty;
        this.base64Data = base64Data;
    }

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

    public void setNonce(int nonce) {
        this.nonce = nonce;
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

    public int getNonce() {
        return nonce;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public ArrayList<String> getBase64Data() {
        return base64Data;
    }
}
