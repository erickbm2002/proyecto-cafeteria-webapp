package cafeteria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cafeteria.Services.ProductoServices;

@Controller
public class ProductoController {

    @Autowired
    private ProductoServices productoServices;
    @GetMapping
    public String producto(Model model) {
        var productos = productoServices.getProductos(false);
        model.addAttribute("productos", productos);
        return "/pages/menu";
    }

}
