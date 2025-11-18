package cafeteria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
@RequestMapping("/admin")
public class AdminGeneralController {

    @GetMapping()
    public String indexAdmin(Model model) {
        return "/layouts/admin_layout";
    }

}
