package master.global.entity;

import java.util.ArrayList;
import java.util.List;

public class Table {
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


}
