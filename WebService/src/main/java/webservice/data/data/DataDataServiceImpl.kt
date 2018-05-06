package webservice.data.data

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import webservice.dataservice.DataDataService
import webservice.vo.DataQueryVo
import webservice.vo.InformationAddResponseVo

@Service
class DataDataServiceImpl : DataDataService
{

    @Value("{masterAddress}")
    lateinit var masterAddress: String

    @Autowired
    lateinit var restTemplate: RestTemplate

    override fun findInformation(blockIndex: Long, offset: Int): DataQueryVo {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addInformation(info: String): InformationAddResponseVo {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}


