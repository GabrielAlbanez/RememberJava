package BasicApi.ApiBasic.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
@RequestMapping("/admin")
public class AdminpageController {
    

    @GetMapping("/Dashbord")
    public String home(Model model) {
        return "dashboard"; // equivale a /WEB-INF/views/home.jsp
    }


}
