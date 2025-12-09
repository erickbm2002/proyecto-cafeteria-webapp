package cafeteria.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cafeteria.Services.ComentarioService;
import cafeteria.domain.Comentario;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/contacto")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping
    public String mostrarContacto(Model model) {
        List<String> archivosCss = Arrays.asList("formulario.css");
        model.addAttribute("archivos_css", archivosCss);
        model.addAttribute("comentario", new Comentario());
        return "pages/contacto";
    }

    @PostMapping("/enviar")
    public String enviarComentario(@Valid @ModelAttribute Comentario comentario,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes,
                                    Model model) {
        
        if (result.hasErrors()) {
            model.addAttribute("error", "Por favor completa todos los campos correctamente");
            return "pages/contacto";
        }

        try {
            comentarioService.save(comentario);
            redirectAttributes.addFlashAttribute("mensaje", "¡Gracias por tu comentario! Lo hemos recibido exitosamente.");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al enviar el comentario: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "danger");
        }

        return "redirect:/contacto";
    }
}