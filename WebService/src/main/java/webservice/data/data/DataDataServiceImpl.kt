package webservice.data.data

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import webservice.dataservice.DataDataService
import webservice.exception.PrivateDataViolationException
import webservice.vo.DataQueryVo
import webservice.vo.InformationAddResponseVo
import webservice.vo.tomaster.ToMasterAddInformationResponse
import webservice.vo.tomaster.ToMasterAddInformationVo
import webservice.vo.tomaster.ToMasterQueryInformationResponse
import webservice.vo.tomaster.ToMasterQueryInformationVo

@Service
class DataDataServiceImpl : DataDataService {


    val SEPARATOR: String = ":):(XD"

    @Value("\${masterAddress}") // localhost:8080
    private
    lateinit var masterAddress: String

    @Autowired private
    lateinit var restTemplate: RestTemplate

    override fun findInformation(blockIndex: Long, offset: Int, accessor: String, isPrivate: Boolean): DataQueryVo {
        val info = ToMasterQueryInformationVo(blockIndex, offset)

        val res = restTemplate
                .getForEntity("$masterAddress/findBlockInfo?blockIndex=$blockIndex&blockOffset=$offset", ToMasterQueryInformationResponse::class.java)
                .body
                .data

        val i = res.lastIndexOf(SEPARATOR)

        val data = res.substring(0,i)
        val owner = res.substring(i)


        if (isPrivate && owner != accessor) {
            throw PrivateDataViolationException(blockIndex, offset, owner, accessor)
        }

        return DataQueryVo(data)
    }

    override fun addInformation(info: String, projectName: String): InformationAddResponseVo? {

        val info = ToMasterAddInformationVo(info+SEPARATOR+projectName)

        val res = restTemplate
                .postForEntity("$masterAddress/saveInfo", info, ToMasterAddInformationResponse::class.java)
                .body ?: return null
        return InformationAddResponseVo(res.blockIndex, res.blockOffset.toInt())
    }

}


