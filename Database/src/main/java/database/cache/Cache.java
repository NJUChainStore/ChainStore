package database.cache;

import database.data.dao.user.BlockDao;
import database.data.daoimpl.user.BlockDaoImpl;
import database.model.Block;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class Cache {
    //每台机器对应的缓存，这是一个优先队列。
    private ArrayList<Block> blocks = new ArrayList<>();

    @Autowired
    private BlockDao blockDao;

    public synchronized boolean addBlock(Block block){
        //向缓存中增加一个区块
        blocks.add(block);
        reSort();//不知道有没有重新排序的必要
        return true;
    }

    public synchronized boolean writeBlock(){
        // 向存储盘中写入一个区块
        if(blocks.size()>0) {
            blockDao.pushSingleBlock(blocks.get(0));
            blocks.remove(0);
        }
        return true;
    }

    public synchronized boolean writeAllBlocks(){
        //向存储盘中写入所有区块
        blockDao.pushAllBlocks(blocks);
        removeAll();
        return true;
    }

    public synchronized void removeAll(){
        // 清空缓存
        while(blocks.size()>0){
            blocks.remove(0);
        }
    }

    private  void reSort(){
        //重新排序
    }

    public  synchronized int findMaxIndex(){
        int max=-1;
        for(Block block:blocks){
            if(block.getIndex()>max){
                max=(int)block.getIndex();
            }
        }
        return max;
    }
}
