package BasicApi.ApiBasic.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class PagesController {
    

    @GetMapping("/home")
    public String home(Model model) {
        return "home"; // equivale a /WEB-INF/views/home.jsp
    }


}
