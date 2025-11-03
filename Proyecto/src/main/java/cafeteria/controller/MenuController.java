package cafeteria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/menu")
public class MenuController {
    
    @GetMapping
    public String menu(Model model) {
        model.addAttribute("titulo_pagina", "Menu | Coffee Corner");
        return "/menu/menu";
    }
}
