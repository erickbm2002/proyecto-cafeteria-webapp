package cafeteria.controller.admin;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import cafeteria.Services.CategoriaServices;
import cafeteria.Services.ProductoServices;
import cafeteria.domain.Producto;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/admin/administrar")
public class ProductoAdminController {

    @Autowired
    private ProductoServices productoServices;

    @Autowired
    private CategoriaServices categoriaServices;

    @GetMapping("/productos")
    public String productosAdmin(Model model) {
        var productosBd = productoServices.getProductos(false);
        var categoriasBd = categoriaServices.getCategorias(true);
        model.addAttribute("productos", productosBd);
        model.addAttribute("categorias", categoriasBd);
        return "pages/admin/productos";
    }

   @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto, 
                                   @RequestParam("file") MultipartFile imagen,
                                   RedirectAttributes redirectAttributes) {
        try {
            productoServices.save(producto, imagen);
            redirectAttributes.addFlashAttribute("mensaje", "Producto guardado exitosamente");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar la imagen");
            e.printStackTrace();
        }
        
        return "redirect:/productos/lista";
    }
    

}