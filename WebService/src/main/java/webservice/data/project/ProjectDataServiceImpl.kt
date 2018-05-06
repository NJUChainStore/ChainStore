package webservice.data.project

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import webservice.data.project.factory.ProjectDaoFactory
import webservice.dataservice.ProjectDataService
import webservice.log.LogService
import webservice.po.project.ProjectPo

import java.sql.SQLException
import java.util.stream.Collectors


@Service
class ProjectDataServiceImpl @Autowired
constructor(private val logService: LogService) : ProjectDataService {
    override fun findProjectByToken(token: String): ProjectPo? {
        return dao.queryBuilder().where().eq("token", token).queryForFirst()
    }

    private val dao = ProjectDaoFactory.projectDao

    private fun handleError(e: SQLException) {
        logService.error("Error occurred during database operation. Detail: ${e.cause}")
        throw e
    }

    override fun addProject(project: ProjectPo) {
        try {
            dao.create(project)
            logService.success("Added project ${project.name}. Id: ${project.id}")
        } catch (e: SQLException) {
            handleError(e)
        }

    }

    override fun updateProject(project: ProjectPo) {
        try {
            dao.update(project)
            logService.success("Updated project (id: ${project.id}")
        } catch (e: SQLException) {
            handleError(e)
        }

    }

    override fun removeProject(id: Int) {
        try {
            dao.deleteById(id)
            logService.success("Removed project (id: $id)")
        } catch (e: SQLException) {
            handleError(e)
        }
    }

    override fun filterProject(predicate: (ProjectPo) -> Boolean): List<ProjectPo> {
        return dao.queryForAll().stream().filter(predicate).collect(Collectors.toList())
    }
}
