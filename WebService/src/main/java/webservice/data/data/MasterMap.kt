package webservice.data.data

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import webservice.config.MasterAddressesConfig
import javax.annotation.PostConstruct

@Component
class MasterMap {

    @Autowired lateinit var masterAddressesConfig: MasterAddressesConfig
    private val map: HashMap<String, Boolean> = HashMap()

    @PostConstruct
    fun postConstruct() {
        println(masterAddressesConfig.masters)
        masterAddressesConfig.masters.forEach { x -> map[x] = true }
    }

    fun invalidate(address: String) {
        if (map.contains(address)) {
            map[address] = false
        }
    }

    fun selectAValid(): String? {
        for (i in map) {
            if (i.value) {
                return i.key
            }
        }
        return null
    }


}
