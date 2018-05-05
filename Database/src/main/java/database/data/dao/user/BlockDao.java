package database.data.dao.user;

import database.model.Block;

import java.util.ArrayList;

public interface BlockDao {
    //根据区块号和偏移量查询单条信息记录
     String getSingleRecord(int blockNum ,int recordNum);

     //删除块号在x之后的所有区块（在错误的情况下）
     void deleteWrongData(int x);

     //写入单个区块
     void pushSingleBlock(Block block);

     //写入Cache 中所有区块
    void pushAllBlocks(ArrayList<Block> blocks);

    //校验数据正确性，返回自然数说明该块号后的数据错误。返回-1 说明全部正确。
    int  check (int maxValue);


}
