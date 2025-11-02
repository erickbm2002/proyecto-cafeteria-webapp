package cafeteria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/menu")
public class Menu {
    
    @GetMapping("/menu")
    public String menu(Model model) {
        model.addAttribute("titulo_pagina", "Menu | Coffee Corner");
        return "/menu/menu";
    }
}
