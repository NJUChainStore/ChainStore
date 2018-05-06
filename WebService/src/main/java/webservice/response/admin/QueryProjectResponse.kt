package webservice.response.admin

import webservice.po.project.ProjectPo
import webservice.response.Response
import webservice.vo.ProjectQueryVo

class QueryProjectResponse(val list: List<ProjectQueryVo>) : Response()
