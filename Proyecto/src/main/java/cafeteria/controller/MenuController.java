package cafeteria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cafeteria.Services.ProductoServices;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private ProductoServices productoServices;
    
    @GetMapping
    public String menu(Model model) {
        var productosDB = productoServices.getProductos(false);
        model.addAttribute("productos", productosDB);
        return "/pages/menu";
    }

}
