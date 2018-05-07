package webservice.data.data

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity
import webservice.dataservice.DataDataService
import webservice.vo.DataQueryVo
import webservice.vo.InformationAddResponseVo
import webservice.vo.tomaster.ToMasterAddInformationResponse
import webservice.vo.tomaster.ToMasterAddInformationVo
import webservice.vo.tomaster.ToMasterQueryInformationResponse
import webservice.vo.tomaster.ToMasterQueryInformationVo

@Service
class DataDataServiceImpl : DataDataService
{

    @Value("{masterAddress}") // localhost:8080
    lateinit var masterAddress: String

    @Autowired
    lateinit var restTemplate: RestTemplate

    override fun findInformation(blockIndex: Long, offset: Int): DataQueryVo {
        val info = ToMasterQueryInformationVo(blockIndex, offset)

        val res = restTemplate
                .getForEntity("$masterAddress/findBlockInfo?blockIndex=$blockIndex&blockOffset=$offset", ToMasterQueryInformationResponse::class.java)
                .body
        return DataQueryVo(res.data)
    }

    override fun addInformation(info: String): InformationAddResponseVo? {

        val info = ToMasterAddInformationVo(info)

        val res = restTemplate
                .postForEntity("$masterAddress/saveInfo", info, ToMasterAddInformationResponse::class.java)
                .body ?: return null
        return InformationAddResponseVo(res.blockIndex, res.blockOffset.toInt())
    }

}


