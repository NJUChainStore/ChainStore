package webservice.dataservice

import webservice.vo.DataQueryVo
import webservice.vo.InformationAddResponseVo


interface DataDataService {
    fun addInformation(info: String): InformationAddResponseVo?

    fun findInformation(blockIndex: Long, offset: Int): DataQueryVo?
}
