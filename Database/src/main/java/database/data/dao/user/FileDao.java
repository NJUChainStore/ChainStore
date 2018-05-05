package database.data.dao.user;

import database.model.Block;

public interface FileDao {
     Block readBlock(int num);
    boolean writeBlock( Block block);
}
