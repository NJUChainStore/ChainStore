package webservice.response

class SuccessResponse(var description: String?) : Response() {
    val infoCode: Int

    init {
        this.infoCode = 10000
    }
}
