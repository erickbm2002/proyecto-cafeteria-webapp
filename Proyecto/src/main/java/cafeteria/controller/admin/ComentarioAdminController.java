package cafeteria.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cafeteria.Services.ComentarioService;

@Controller
@RequestMapping("/admin/comentarios")
public class ComentarioAdminController {

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping
    public String listarComentarios(Model model) {
        var comentarios = comentarioService.getAllComentarios();
        model.addAttribute("comentarios", comentarios);
        return "pages/admin/comentarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarComentario(@PathVariable Integer id, 
                                      RedirectAttributes redirectAttributes) {
        try {
            comentarioService.delete(id);
            redirectAttributes.addFlashAttribute("mensaje", "Comentario eliminado exitosamente");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el comentario: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "danger");
        }
        return "redirect:/admin/comentarios";
    }
}