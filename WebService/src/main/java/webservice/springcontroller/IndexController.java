package webservice.springcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class IndexController {
    @RequestMapping(method = RequestMethod.GET, path = "/")
    public RedirectView index() {
        return new RedirectView("/swagger-ui.html");
    }
}
