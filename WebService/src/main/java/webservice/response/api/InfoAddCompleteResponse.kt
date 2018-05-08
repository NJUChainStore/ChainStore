package webservice.response.api

import webservice.response.Response

class InfoAddCompleteResponse(blockIndex: Long, offset: Int) : Response() {
    var blockIndex: Long = blockIndex
    var offset: Int = offset

}
