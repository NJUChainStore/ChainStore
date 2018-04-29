package database.data.daoimpl.user;

import database.data.dao.user.BlockDao;
import database.model.Block;

import java.util.ArrayList;

public class BlockDaoImpl implements BlockDao {
    @Override
    public String getSingleRecord(int blockNum, int recordNum) {
        return null;
    }

    @Override
    public void deleteWrongData(int x) {

    }

    @Override
    public void pushSingleBlock(Block block) {

    }

    @Override
    public void pushAllBlocks(ArrayList<Block> blocks) {
         for(Block block:blocks){
             pushSingleBlock(block);
         }
    }

    @Override
    public int check() {
        return 0;
    }
}
