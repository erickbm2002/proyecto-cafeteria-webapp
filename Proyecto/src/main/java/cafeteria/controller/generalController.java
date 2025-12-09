package cafeteria.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GeneralController {

    @GetMapping("/inicio")
    public String index() {
        return "/pages/index";
    }
}