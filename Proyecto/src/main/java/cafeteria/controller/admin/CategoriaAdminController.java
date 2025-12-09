package cafeteria.controller.admin;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import cafeteria.Services.CategoriaServices;
import cafeteria.domain.Categoria;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/administrar")
public class CategoriaAdminController {

    @Autowired
    private CategoriaServices categoriaServices;

    @GetMapping("/Categorias")
    public String categoriasAdmin(Model model) {
        var categoriasBd = categoriaServices.getCategorias(false);
        model.addAttribute("categorias", categoriasBd);
        model.addAttribute("categoria", new Categoria()); // Para el formulario
        List<String> archivosCss = Arrays.asList("admin/admin_categorias.css");
        model.addAttribute("archivos_css", archivosCss);
        return "pages/admin/categorias";
    }

    @PostMapping("/categorias/guardar")
    public String guardarCategoria(@Valid @ModelAttribute Categoria categoria,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes,
                                    Model model) {
        
        // Si hay errores de validación
        if (result.hasErrors()) {
            var categoriasBd = categoriaServices.getCategorias(false);
            model.addAttribute("categorias", categoriasBd);
            model.addAttribute("error", "Por favor corrige los errores en el formulario");
            return "pages/admin/categorias";
        }

        try {
            categoriaServices.save(categoria);
            redirectAttributes.addFlashAttribute("mensaje", "Categoría guardada exitosamente");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar la categoría: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "danger");
            e.printStackTrace();
        }
        
        return "redirect:/admin/administrar/Categorias";
    }

    @GetMapping("/categorias/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            categoriaServices.delete(id);
            redirectAttributes.addFlashAttribute("mensaje", "Categoría eliminada exitosamente");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la categoría: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "danger");
        }
        return "redirect:/admin/administrar/Categorias";
    }

    @PostMapping("/categorias/toggle-activo/{id}")
    public String toggleActivo(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            categoriaServices.toggleActivo(id);
            redirectAttributes.addFlashAttribute("mensaje", "Estado actualizado exitosamente");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el estado: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "danger");
        }
        return "redirect:/admin/administrar/Categorias";
    }
}