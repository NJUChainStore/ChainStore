package webservice.po.project

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

@DatabaseTable(tableName = "ProjectPo")
class ProjectPo constructor() {
    @DatabaseField
    lateinit var name: String
    @DatabaseField(generatedId = true)
    var id: Int = 0
    @DatabaseField
    lateinit var token: String
    @DatabaseField
    var infoAddedCount: Int = 0
    @DatabaseField
    var isPrivate: Boolean = false

    constructor(name: String, isPrivate: Boolean) : this() {
        this.name = name
        this.isPrivate = isPrivate
        this.token = UUID.nameUUIDFromBytes(name.toByteArray()).toString()
    }

}
