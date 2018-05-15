package webservice.io

import webservice.WebService

import java.net.URL
import java.util.Arrays
import java.util.stream.Collectors
import java.io.File

object IOUtil {
    fun getResource(path: String): URL {
        return WebService::class.java.getResource(path)
    }
    @JvmStatic
    fun getFilePathUnderRootDirOfJarFileOrClassDir(relativePath: String): String {
        //        String path = MainApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        val path = WebService::class.java.protectionDomain.codeSource.location.path
        val paths = path.split("!".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val filePath = paths[0]

        val jarFile = File(paths[0])
        val parentDir = Arrays.stream(jarFile.parent.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
                .skip(1).collect(Collectors.joining(":"))


        println(parentDir)
        return parentDir + relativePath

        //        return path + ".." + relativePath;
    }

}
