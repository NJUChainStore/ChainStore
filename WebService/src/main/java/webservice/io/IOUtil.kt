package webservice.io

import webservice.WebService


object IOUtil {
    @JvmStatic
    fun getFilePathUnderRootDirOfJarFileOrClassDir(relativePath: String): String {
        return WebService::class.java.protectionDomain.codeSource.location.path + "/.." + relativePath
    }
}

