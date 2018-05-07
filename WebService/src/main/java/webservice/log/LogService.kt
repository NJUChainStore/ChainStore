package webservice.log

interface LogService {
    fun info(content: String)

    fun error(content: String)

    fun warning(content: String)

    fun success(content: String)
}
