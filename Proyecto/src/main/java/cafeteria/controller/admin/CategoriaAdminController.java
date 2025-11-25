package cafeteria.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import cafeteria.Services.CategoriaServices;


@Controller
@RequestMapping("/admin/administrar")
public class CategoriaAdminController {

    @Autowired
    private CategoriaServices categoriaServices;

    @GetMapping("/Categorias")
    public String categoriasAdmin(Model model) {
        var categoriasBd = categoriaServices.getCategorias(false);
        model.addAttribute("categorias", categoriasBd);
        return "pages/admin/categorias";
    }
    
}
