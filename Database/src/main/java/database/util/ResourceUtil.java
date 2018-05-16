package database.util;

import database.Database;

import  java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ResourceUtil {
    public static int randomPath=-1;
    public static URL getResource(String path) {
        return Database.class.getResource(path);
    }

    public static String getFilePathUnderRootDirOfJarFileOrClassDir(String relativePath) {
//        String path = MainApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String path = Database.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String[] paths = path.split("!");
        String filePath = paths[0];

        File jarFile = new File(paths[0]);
        String parentDir = Arrays.stream(jarFile.getParent().split(":"))
                .skip(1).collect(Collectors.joining(":"));


        System.out.println(parentDir);
        return parentDir + relativePath;

    }
    public  static int getRandomPath(){
        if(randomPath>=0){
            return randomPath;
        }else{
            randomPath=(int)(Math.random()*100000);
            return randomPath;
        }
    }
    public static boolean mkDirectory() {
        String path=getFilePathUnderRootDirOfJarFileOrClassDir("")+"/"+getRandomPath();
        File file =null;
        try {
            file = new File(path);
            if (!file.exists()) {
                return file.mkdirs();
            }
            else{
                return false;
            }
        } catch (Exception e) {
        } finally {
            file = null;
        }
        return false;
    }
}
