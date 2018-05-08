package webservice.data.project.factory

import webservice.db.BaseDatabaseFactory
import webservice.po.project.ProjectPo

class ProjectDaoFactory : BaseDatabaseFactory() {
    companion object {
        val projectDao = BaseDatabaseFactory.createDao<ProjectPo, Int>(ProjectPo::class.java)
    }
}

