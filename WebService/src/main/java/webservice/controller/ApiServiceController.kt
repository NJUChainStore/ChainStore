package webservice.controller

import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import webservice.dataservice.DataDataService
import webservice.dataservice.ProjectDataService
import webservice.exception.PrivateDataViolationException
import webservice.response.Response
import webservice.response.WrongResponse
import webservice.response.api.DataGetResponse
import webservice.response.api.InfoAddCompleteResponse
import webservice.vo.InformationAddVo

@RestController
class ApiServiceController
@Autowired
constructor(private val projectDataService: ProjectDataService, private val dataDataService: DataDataService) {

    @ApiOperation(value = "增加信息", notes = "增加信息")
    @RequestMapping(value = ["/data"], method = [(RequestMethod.POST)])
    @ApiResponses(value = [
        (ApiResponse(code = 200, message = "成功为此项目增加一条记录。返回信息所在区块编号和区块内偏移。", response = InfoAddCompleteResponse::class)),
        (ApiResponse(code = 403, message = "Token not valid"))
    ])
    @ResponseBody
    fun add(@RequestBody info: InformationAddVo): ResponseEntity<Response>? {

        val project = projectDataService.findProjectByToken(info.token)
                ?: return ResponseEntity(WrongResponse(403, "Token not valid"), HttpStatus.FORBIDDEN)

        project.infoAddedCount++
        projectDataService.updateProject(project)

        val addRes = dataDataService.addInformation(info.info, project.name)
        if (addRes != null) {
            return ResponseEntity(InfoAddCompleteResponse(addRes.blockIndex, addRes.offset), HttpStatus.OK)
        } else {
            return ResponseEntity(WrongResponse(10002, "fail to add"), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @ApiOperation(value = "查找信息", notes = "查找信息")
    @RequestMapping(value = ["/data"], method = [(RequestMethod.GET)])
    @ApiResponses(value = [
        (ApiResponse(code = 200, message = "Returns the data", response = DataGetResponse::class)),
        (ApiResponse(code = 401, message = "Token not valid.")),
        (ApiResponse(code = 403, message = "Accessor is not the owner of the project od the data")),
        (ApiResponse(code = 404, message = "Token valid, blockIndex or offset not valid"))
    ])
    @ResponseBody
    fun getInformation(@RequestParam("blockIndex") blockIndex: Long,
                       @RequestParam("offset") offset: Int,
                       @RequestParam("token") token: String): ResponseEntity<Response>? {

        val project = projectDataService.findProjectByToken(token)
                ?: return ResponseEntity(WrongResponse(401, "Token not valid"), HttpStatus.UNAUTHORIZED)

        try {
            val res = dataDataService.findInformation(blockIndex, offset, project.name, project.isPrivate)
                    ?: return ResponseEntity(WrongResponse(404, "Not Found"), HttpStatus.NOT_FOUND)
            return ResponseEntity(DataGetResponse(res.info), HttpStatus.OK)
        } catch (e: PrivateDataViolationException) {
            return ResponseEntity(WrongResponse(403, "Accessor is not the owner of the project of the data"), HttpStatus.FORBIDDEN)
        }

    }
}
