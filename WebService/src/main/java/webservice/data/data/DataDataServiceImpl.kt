package webservice.data.data

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import webservice.dataservice.DataDataService
import webservice.exception.AllMasterDeadException
import webservice.exception.PrivateDataViolationException
import webservice.vo.DataQueryVo
import webservice.vo.InformationAddResponseVo
import webservice.vo.tomaster.ToMasterAddInformationResponse
import webservice.vo.tomaster.ToMasterAddInformationVo
import webservice.vo.tomaster.ToMasterQueryInformationResponse
import webservice.vo.tomaster.ToMasterQueryInformationVo
import javax.annotation.PostConstruct

@Service
class DataDataServiceImpl : DataDataService {


    val SEPARATOR: String = ":):(XD"

    @Value("\${masterAddresses}") // localhost:8080
    private
    lateinit var masterAddresses: List<String>

    lateinit var map: MasterMap

    @PostConstruct
    fun initMasterAddressMap() {
        map = MasterMap(masterAddresses)
    }

    @Autowired
    private
    lateinit var restTemplate: RestTemplate

    override fun findInformation(blockIndex: Long, offset: Int, accessor: String, isPrivate: Boolean): DataQueryVo {
        val info = ToMasterQueryInformationVo(blockIndex, offset)

        var url = map.selectAValid()

        while (url != null) {
            val res = restTemplate
                    .getForEntity("$url/findBlockInfo?blockIndex=$blockIndex&blockOffset=$offset", ToMasterQueryInformationResponse::class.java)
            if (res.statusCode.is2xxSuccessful) {
                val resData = res.body.data
                val i = resData.lastIndexOf(SEPARATOR)

                val data = resData.substring(0, i)
                val owner = resData.substring(i)


                if (isPrivate && owner != accessor) {
                    throw PrivateDataViolationException(blockIndex, offset, owner, accessor)
                }

                return DataQueryVo(data)
            } else {
                map.invalidate(url)
                url = map.selectAValid()
            }
        }

        throw AllMasterDeadException()


    }

    override fun addInformation(info: String, projectName: String): InformationAddResponseVo? {

        val info = ToMasterAddInformationVo(info + SEPARATOR + projectName)

        var url = map.selectAValid()
        while (url != null) {
            val res = restTemplate
                    .postForEntity("$url/saveInfo", info, ToMasterAddInformationResponse::class.java)
            if (res.statusCode.is2xxSuccessful) {
                val resData = res.body ?: return null
                return InformationAddResponseVo(resData.blockIndex, resData.blockOffset.toInt())
            } else {
                map.invalidate(url)
                url = map.selectAValid()
            }

        }

        throw AllMasterDeadException()


    }

}


