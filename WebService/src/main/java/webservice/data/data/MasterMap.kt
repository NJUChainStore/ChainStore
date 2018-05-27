package webservice.data.data

class MasterMap constructor(masterAddresses: Array<String>) {
    private val map: HashMap<String, Boolean> = HashMap()

    init {
        masterAddresses.forEach { x -> map[x] = true }
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
