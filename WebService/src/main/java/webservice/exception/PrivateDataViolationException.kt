package webservice.exception

class PrivateDataViolationException(val blockIndex: Long,
                                    val offset: Int,
                                    val owner: String,
                                    val accessor: String
) : RuntimeException()
