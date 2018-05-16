package database.data.daoimpl.user;

import database.config.RegisterConfig;
import database.data.dao.user.FileDao;
import database.model.Block;
import database.util.ResourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;

@Service
public class FileDaoImpl implements FileDao {

    @Autowired
    RegisterConfig config;

    @Override
    public Block readBlock(int num) {
        String tempPath=ResourceUtil.getFilePathUnderRootDirOfJarFileOrClassDir("");
        Block block = new Block();
        block.setIndex(num);
        
        try {
            File file = ResourceUtils.getFile(tempPath+"/"+config.serverPort+"/" + num + ".txt");

            BufferedReader br = new BufferedReader(new FileReader(file));
            block.setHash(br.readLine());
            block.setPreviousHash(br.readLine());

            block.setNonce(Integer.parseInt(br.readLine()));
            block.setTimestamp(Long.parseLong(br.readLine()));
            String content = "";
            ArrayList<String> data = new ArrayList<String>();
            while ((content = br.readLine()) != null) {

                data.add(content);

            }
            block.setBase64Data(data);
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("File path Error");
            return null;
            //e.printStackTrace();
        } catch (IOException e) {
            return null;
           // e.printStackTrace();
        }
        return block;
    }

    @Override
    public boolean writeBlock(Block block) {
        String fileName = "" + block.getIndex();
        try {

            String tempPath=ResourceUtil.getFilePathUnderRootDirOfJarFileOrClassDir("");
            File file0 = new File(tempPath + "/"+config.serverPort+"/" + fileName + ".txt");
            if (!file0.exists()) {

                file0.createNewFile();
            }
            File file = ResourceUtils.getFile(tempPath+ "/"+config.serverPort+"/" + fileName + ".txt");

            FileWriter writer = new FileWriter(file, false);
            /**
             * 注意写入的顺序。读的时候要按这个顺序读。
             */
            writer.write(block.getHash() + "\n");
            writer.write(block.getPreviousHash() + "\n");
            writer.write(block.getNonce() + "\n");
            writer.write(block.getTimestamp() + "\n");
            for (String tuple : block.getBase64Data()) {
                writer.write(tuple + "\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("领域对象在写入数据库文件时发生异常");

            e.printStackTrace();
        }
        return false;
    }
}
