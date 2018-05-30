package webservice.data.data

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import webservice.config.MasterAddressesConfig
import javax.annotation.PostConstruct
import kotlin.coroutines.experimental.buildSequence

@Component
class MasterMap constructor(@Autowired var masterAddressesConfig: MasterAddressesConfig){

    private val masters: List<String>

    init {
        masters = ArrayList(masterAddressesConfig.masters)
    }

    private var current = 0

    fun select() = buildSequence {
        var count = 0
        while (count < masters.size) {
            yield(masters[current])
            current = (current+1)%(masters.size)
            count++
        }
    }


}
