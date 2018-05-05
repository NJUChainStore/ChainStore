package master.global.entity;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private long nowBlockIndex = 0;
    private String previousHash = "0";
    private MinerItem miner;
    private List<DatabaseItem> databases = new ArrayList<>();

    public MinerItem getMiner() {
        return miner;
    }

    public void setMiner(MinerItem miner) {
        this.miner = miner;
    }

    public List<DatabaseItem> getDatabases() {
        return databases;
    }

    public void setDatabases(List<DatabaseItem> databases) {
        this.databases = databases;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public long getNowBlockIndex() {
        return nowBlockIndex;
    }

    public void setNowBlockIndex(long nowBlockIndex) {
        this.nowBlockIndex = nowBlockIndex;
    }

    public void updateBlockIndex() {
        nowBlockIndex++;
    }
}
