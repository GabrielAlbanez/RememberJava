
package BasicApi.ApiBasic.Controllers;



import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class ControllerTeste {

    @GetMapping("/teste")

    @ResponseBody
    public String teste() {

        return "Teste ok";

    }

}
