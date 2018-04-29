package database.model;

import database.data.dao.user.BlockDao;
import database.data.daoimpl.user.BlockDaoImpl;

import java.util.ArrayList;

public class Cache {
    //每台机器对应的缓存，这是一个优先队列。
    private ArrayList<Block> blocks=new ArrayList<Block>();
    BlockDao blockDao=new BlockDaoImpl();
    public boolean addBlock(Block block){
        //向缓存中增加一个区块
        blocks.add(block);
        reSort();//不知道有没有重新排序的必要
        return true;
    }

    public boolean writeBlock(){
        // 向存储盘中写入一个区块
        if(blocks.size()>0) {
            blockDao.pushSingleBlock(blocks.get(0));
            blocks.remove(0);
        }
        return true;
    }

    public boolean writeAllBlocks(){
        //向存储盘中写入所有区块
        blockDao.pushAllBlocks(this.blocks);
        removeAll();
        return true;
    }

    public void removeAll(){
        // 清空缓存
        while(blocks.size()>0){
            blocks.remove(0);
        }
    }

    private void reSort(){
        //重新排序
    }
}
