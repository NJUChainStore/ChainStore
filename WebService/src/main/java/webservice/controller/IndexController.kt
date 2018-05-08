package webservice.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.view.RedirectView

class IndexController {
    @RequestMapping(method = [(RequestMethod.GET)], path = ["/"])
    fun index(): RedirectView {
        return RedirectView("/index.html")
    }
}

