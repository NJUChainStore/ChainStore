package database.cache;


import database.model.Block;
import org.junit.Test;

public class CacheTest {


    @Test
    public void findMaxIndex() {
        Block block=new Block();
        block.setIndex(20);
        Cache.addBlock(block);
        Block block1=new Block();
        block1.setIndex(40);
        Cache.addBlock(block1);
        System.out.println(Cache.findMaxIndex());
    }
}
