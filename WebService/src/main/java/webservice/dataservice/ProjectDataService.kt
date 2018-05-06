package webservice.dataservice


import webservice.po.project.ProjectPo


interface ProjectDataService {
    fun addProject(project: ProjectPo)

    fun updateProject(project: ProjectPo)

    fun removeProject(id: Int)

    fun filterProject(predicate: (ProjectPo) -> Boolean): List<ProjectPo>

    fun findProjectByToken(token: String) : ProjectPo?

}
