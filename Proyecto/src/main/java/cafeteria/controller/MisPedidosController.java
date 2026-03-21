package cafeteria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cafeteria.Services.OrdenService;
import cafeteria.Services.UsuarioService;
import cafeteria.domain.Orden;
import cafeteria.domain.Usuario;

@Controller
@RequestMapping("/mis-pedidos")
public class MisPedidosController {

    @Autowired
    private OrdenService ordenService;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Listar todos los pedidos del usuario autenticado
     */
    @GetMapping
    public String listarMisPedidos(Authentication authentication,
            Model model,
            RedirectAttributes redirectAttributes) {

        // Verificar autenticación
        if (authentication == null || !authentication.isAuthenticated()) {
            redirectAttributes.addFlashAttribute("error",
                    "Debes iniciar sesión para ver tus pedidos");
            return "redirect:/login";
        }

        try {

            String correo = authentication.getName();
            Usuario usuario = usuarioService.getUsuarioByCorreo(correo);


            List<Orden> ordenes = ordenService.getOrdenesByUsuario(usuario);

            model.addAttribute("ordenes", ordenes);
            return "pages/mis-pedidos";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Error al cargar tus pedidos: " + e.getMessage());
            return "redirect:/inicio";
        }
    }


    @GetMapping("/{id}")
    public String verDetallePedido(@PathVariable Integer id,
            Authentication authentication,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (authentication == null || !authentication.isAuthenticated()) {
            redirectAttributes.addFlashAttribute("error",
                    "Debes iniciar sesión para ver este pedido");
            return "redirect:/login";
        }

        try {
 
            String correo = authentication.getName();
            Usuario usuario = usuarioService.getUsuarioByCorreo(correo);


            Orden orden = ordenService.getOrdenById(id);


            if (!orden.getUsuario().getIdUsuario().equals(usuario.getIdUsuario())) {
                redirectAttributes.addFlashAttribute("error",
                        "No tienes permiso para ver este pedido");
                return "redirect:/mis-pedidos";
            }

            model.addAttribute("orden", orden);
            return "pages/detalle-pedido";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Error al cargar el pedido: " + e.getMessage());
            return "redirect:/mis-pedidos";
        }
    }
}