package webservice.io

import webservice.WebService
import java.io.File
import java.util.*
import java.util.stream.Collectors


object IOUtil {
    @JvmStatic
    fun getFilePathUnderRootDirOfJarFileOrClassDir(relativePath: String): String {
        val path = WebService::class.java.protectionDomain.codeSource.location.path
        val paths = path.split("!".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
        val filePath = paths[0]

        val jarFile = File(paths[0])
        val parentDir = Arrays.stream<String>(jarFile.parent.split(":".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray())
                .skip(1).collect(Collectors.joining(":"))
        println(parentDir)
        return parentDir + relativePath
    }
}

