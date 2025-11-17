package cafeteria.controller;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GeneralController {

    @GetMapping("/inicio")
    public String index() {
        return "/pages/index";
    }

    @GetMapping("/contacto")
    public String contacto(Model model) {
        List<String> archivosCss = Arrays.asList("formulario.css");
        model.addAttribute("archivos_css", archivosCss);
        return "/pages/contacto";

    }

}
