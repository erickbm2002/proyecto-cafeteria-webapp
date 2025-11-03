package cafeteria.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GeneralController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("titulo_pagina", "Inicio | Coffee Corner");
        return "index";
    }

    @GetMapping("/contacto")
    public String contacto(Model model) {
        model.addAttribute("titulo_pagina", "Contacto | Coffee Corner");
        return "contacto";
    }

}
