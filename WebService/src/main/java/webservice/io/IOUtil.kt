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
        val path = File(".").absolutePath + relativePath
        println(path)
        return path
    }

}
