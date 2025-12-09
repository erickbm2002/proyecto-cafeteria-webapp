package cafeteria.controller.admin;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import cafeteria.Services.CategoriaServices;
import cafeteria.Services.ProductoServices;
import cafeteria.domain.Producto;
import jakarta.validation.Valid;

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
        model.addAttribute("producto", new Producto()); // Para el formulario chicos
        List<String> archivosCss = Arrays.asList("admin/admin_productos.css");
        model.addAttribute("archivos_css", archivosCss);
        return "pages/admin/productos";
    }

    @PostMapping("/productos/guardar")
    public String guardarProducto(@Valid @ModelAttribute Producto producto, 
                                   BindingResult result,
                                   @RequestParam(value = "file", required = false) MultipartFile imagen,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {
        
        if (result.hasErrors()) {
            var productosBd = productoServices.getProductos(false);
            var categoriasBd = categoriaServices.getCategorias(true);
            model.addAttribute("productos", productosBd);
            model.addAttribute("categorias", categoriasBd);
            model.addAttribute("error", "Por favor corrige los errores en el formulario");
            return "pages/admin/productos";
        }

        try {
            productoServices.save(producto, imagen);
            redirectAttributes.addFlashAttribute("mensaje", "Producto guardado exitosamente");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar la imagen: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "danger");
            e.printStackTrace();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el producto: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "danger");
            e.printStackTrace();
        }
        
        return "redirect:/admin/administrar/productos";
    }

    @GetMapping("/productos/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            productoServices.delete(id);
            redirectAttributes.addFlashAttribute("mensaje", "Producto eliminado exitosamente");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el producto: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "danger");
        }
        return "redirect:/admin/administrar/productos";
    }

    @PostMapping("/productos/toggle-activo/{id}")
    public String toggleActivo(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            productoServices.toggleActivo(id);
            redirectAttributes.addFlashAttribute("mensaje", "Estado actualizado exitosamente");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el estado: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "danger");
        }
        return "redirect:/admin/administrar/productos";
    }
}