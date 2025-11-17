package cafeteria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cafeteria.Services.ProductoServices;

@Controller
public class DevelopentController {

    @Autowired
    private ProductoServices productoServices;    

    /* @GetMapping("/dev/preview-component")
    public String previewComponent(Model model) {
        var productosDB = productoServices.getProductos(false);
        System.out.println("Productos obtenidos:" + productosDB.size());
        model.addAttribute("productos", productosDB);
        return "fragments/components/card-producto";
    } */

    @GetMapping("/dev/preview-component")
    public String previewComponent(Model model) {
        return "pages/index";
    }
}
