package cafeteria.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/admin/administrar")
public class ProductoAdminController {

    @GetMapping("/productos")
    public String administrarProductos(Model model) {
        
        return "/admin/administrar/productos_admin";
    }
    

}