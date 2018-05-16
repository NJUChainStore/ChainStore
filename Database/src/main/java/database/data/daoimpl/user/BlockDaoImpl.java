package database.data.daoimpl.user;

import database.data.dao.user.BlockDao;
import database.data.dao.user.FileDao;
import database.model.Block;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class BlockDaoImpl implements BlockDao {

    final FileDao fileDao;

    @Autowired
    public BlockDaoImpl(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    @Override
    public String getSingleRecord(int blockNum, int recordNum) {
        Block block = fileDao.readBlock(blockNum);
        String res = block.getBase64Data().get(recordNum);
        return res;
    }

    @Override
    public void deleteWrongData(int x) {

    }

    @Override
    public void pushSingleBlock(Block block) {
        fileDao.writeBlock(block);
    }

    @Override
    public void pushAllBlocks(ArrayList<Block> blocks) {
        for (Block block : blocks) {
            pushSingleBlock(block);
        }
    }

    @Override
    public int check(int maxValue) {
        for (int i = 0; i <= maxValue; i++) {
            Block tempBlock = fileDao.readBlock(i);
            if(tempBlock==null){
                return i;
            }
            if (calculateHash(tempBlock.getPreviousHash(), tempBlock.getTimestamp(), tempBlock.getNonce(), tempBlock.getBase64Data()).equals(tempBlock.getHash())) {
                //数据正确
                if (i < maxValue) {
                    if (tempBlock.getHash().equals(fileDao.readBlock(i + 1).getPreviousHash())) {
                        //数据正确
                    } else {
                        return i;
                        //数据不正确，返回从哪一块开始错的。
                    }
                }
            } else {
                return i;
                //数据不正确，返回从哪一块开始错的。
            }

        }

        return -1;
    }

    protected String calculateHash(String previousHash, long timeStamp, int nonce, ArrayList<String> data) {
        String calculatedhash = applySha256(
                previousHash +
                        Long.toString(timeStamp) +
                        Integer.toString(nonce) +
                        data.stream().collect(Collectors.joining("/"))
        );
        return calculatedhash;
    }

    protected String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            //对输入使用 sha256 算法
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte aHash : hash) {
                String hex = Integer.toHexString(0xff & aHash);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
