package mining.parameters;

import java.util.ArrayList;

public class MineParameter {
    private String previousHash;
    private int difficulty;
    private ArrayList<String> base64Data;

    public MineParameter() {
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

    public ArrayList<String> getBase64Data() {
        return base64Data;
    }

    public void setBase64Data(ArrayList<String> base64Data) {
        this.base64Data = base64Data;
    }
}