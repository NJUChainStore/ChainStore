package webservice.controller

import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import webservice.dataservice.ProjectDataService
import webservice.vo.ProjectAddVo
import webservice.po.project.ProjectPo
import webservice.response.Response
import webservice.response.WrongResponse
import webservice.response.admin.ProjectAddResponse
import webservice.response.admin.QueryProjectResponse
import webservice.vo.ProjectQueryVo

@RestController
class ManagementController
@Autowired
constructor(private val projectDataService: ProjectDataService)
{
    @ApiOperation(value = "增加一个项目", notes = "增加一个项目")
    @RequestMapping(value = ["/admin/project"], method = [(RequestMethod.POST)])
    @ApiResponses(value = [
        (ApiResponse(code = 201, message = "成功增加一个项目", response = ProjectAddResponse::class)),
        (ApiResponse(code = 409, message = "Project name exists"))
    ])
    @ResponseBody
    fun addProject(@RequestBody info: ProjectAddVo): ResponseEntity<Response>? {
        if (projectDataService.filterProject { p -> p.name == info.projectName }.any()) {
            return ResponseEntity(WrongResponse(409, "Project already exists"), HttpStatus.CONFLICT )
        }

        val newProject = ProjectPo(info.projectName)
        projectDataService.addProject(newProject)

        return ResponseEntity(ProjectAddResponse(newProject.token), HttpStatus.CREATED)


    }

    @ApiOperation(value = "查询现有所有项目", notes = "查询现有所有项目")
    @RequestMapping(value = ["/admin/project"], method = [(RequestMethod.GET)])
    @ApiResponses(value = [
        (ApiResponse(code = 200, message = "返回现有所有项目", response = QueryProjectResponse::class))
    ])
    @ResponseBody
    fun getAllProjects(): ResponseEntity<Response>? {

        val list = projectDataService.filterProject { _ -> true }
                .map { x -> ProjectQueryVo(x.id, x.name, x.token, x.infoAddedCount) }
                .toList()

        return ResponseEntity(QueryProjectResponse(list), HttpStatus.OK)


    }
}
