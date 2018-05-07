package webservice.response

class SaveInfoResponse : Response {
    var blockIndex: Long = 0
    var blockOffset: Long = 0

    constructor() {}

    constructor(blockIndex: Long, blockOffset: Long) {
        this.blockIndex = blockIndex
        this.blockOffset = blockOffset
    }
}
