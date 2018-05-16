package database.util;

import database.Database;
import database.config.RegisterConfig;
import org.springframework.beans.factory.annotation.Autowired;

import  java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ResourceUtil {
    @Autowired
    RegisterConfig config;

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


}
