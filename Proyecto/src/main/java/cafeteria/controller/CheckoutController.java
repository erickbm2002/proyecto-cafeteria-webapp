package cafeteria.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cafeteria.Services.CarritoService;
import cafeteria.Services.OrdenService;
import cafeteria.Services.UsuarioService;
import cafeteria.domain.DetalleOrden;
import cafeteria.domain.ItemCarrito;
import cafeteria.domain.Orden;
import cafeteria.domain.Usuario;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private OrdenService ordenService;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Mostrar página de checkout
     */
    @GetMapping
    public String mostrarCheckout(Authentication authentication,
            Model model,
            RedirectAttributes redirectAttributes) {

        // Verificar que el usuario esté autenticado
        if (authentication == null || !authentication.isAuthenticated()) {
            redirectAttributes.addFlashAttribute("error",
                    "Debes iniciar sesión para realizar una compra");
            redirectAttributes.addFlashAttribute("tipo", "warning");
            return "redirect:/login";
        }

        // Verificar que el carrito no esté vacío
        if (carritoService.estaVacio()) {
            redirectAttributes.addFlashAttribute("error", "El carrito está vacío");
            redirectAttributes.addFlashAttribute("tipo", "warning");
            return "redirect:/carrito";
        }

        // Verificar disponibilidad de productos
        if (!carritoService.verificarDisponibilidad()) {
            redirectAttributes.addFlashAttribute("error",
                    "Algunos productos no están disponibles. Por favor revisa tu carrito.");
            redirectAttributes.addFlashAttribute("tipo", "danger");
            return "redirect:/carrito";
        }

        // Obtener usuario
        String correo = authentication.getName();
        Usuario usuario = usuarioService.getUsuarioByCorreo(correo);

        // Preparar datos para la vista
        model.addAttribute("usuario", usuario);
        model.addAttribute("items", carritoService.getItems());
        model.addAttribute("cantidadTotal", carritoService.getCantidadTotal());
        model.addAttribute("subtotal", carritoService.getTotal());
        model.addAttribute("totalConDescuentos", carritoService.getTotalConDescuentos());
        model.addAttribute("ahorroTotal", carritoService.getAhorroTotal());

        return "pages/checkout";
    }

    /**
     * Procesar el pago y crear la orden
     */
    @PostMapping("/procesar")
    public String procesarPago(Authentication authentication,
            @RequestParam String metodoPago,
            @RequestParam(required = false) String direccionEnvio,
            @RequestParam(required = false) String notasEspeciales,
            RedirectAttributes redirectAttributes) {

        try {
            // Verificar autenticación
            if (authentication == null || !authentication.isAuthenticated()) {
                redirectAttributes.addFlashAttribute("error",
                        "Debes iniciar sesión para realizar una compra");
                return "redirect:/login";
            }

            // Verificar carrito
            if (carritoService.estaVacio()) {
                redirectAttributes.addFlashAttribute("error", "El carrito está vacío");
                return "redirect:/carrito";
            }

            // Verificar disponibilidad
            if (!carritoService.verificarDisponibilidad()) {
                redirectAttributes.addFlashAttribute("error",
                        "Algunos productos ya no están disponibles");
                return "redirect:/carrito";
            }

            // Obtener usuario
            String correo = authentication.getName();
            Usuario usuario = usuarioService.getUsuarioByCorreo(correo);

            // Crear detalles de la orden desde el carrito
            List<DetalleOrden> detalles = new ArrayList<>();
            for (ItemCarrito item : carritoService.getItems()) {
                DetalleOrden detalle = new DetalleOrden();
                detalle.setProducto(item.getProducto());
                detalle.setCantidad(item.getCantidad());
                detalle.setPrecioUnitario(item.getPrecioConDescuento());
                detalles.add(detalle);
            }

            // Crear la orden (esto decrementará automáticamente el inventario)
            Orden orden = ordenService.crearOrden(usuario, detalles);

            // Vaciar el carrito después de crear la orden exitosamente
            carritoService.vaciarCarrito();

            // Redirigir a página de confirmación
            redirectAttributes.addFlashAttribute("mensaje",
                    "¡Pedido realizado exitosamente! Número de orden: " + orden.getIdOrden());
            redirectAttributes.addFlashAttribute("tipo", "success");
            redirectAttributes.addFlashAttribute("ordenId", orden.getIdOrden());

            return "redirect:/checkout/confirmacion/" + orden.getIdOrden();

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Error al procesar el pago: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "danger");
            return "redirect:/checkout";
        }
    }

    /**
     * Página de confirmación de orden
     */
    @GetMapping("/confirmacion/{idOrden}")
    public String confirmacion(@PathVariable Integer idOrden,
            Authentication authentication,
            Model model,
            RedirectAttributes redirectAttributes) {

        try {
            // Verificar autenticación
            if (authentication == null || !authentication.isAuthenticated()) {
                return "redirect:/login";
            }

            // Obtener la orden
            Orden orden = ordenService.getOrdenById(idOrden);

            // Verificar que la orden pertenezca al usuario actual
            String correo = authentication.getName();
            Usuario usuario = usuarioService.getUsuarioByCorreo(correo);

            if (!orden.getUsuario().getIdUsuario().equals(usuario.getIdUsuario())) {
                redirectAttributes.addFlashAttribute("error",
                        "No tienes permiso para ver esta orden");
                return "redirect:/inicio";
            }

            model.addAttribute("orden", orden);
            return "pages/confirmacion-orden";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Error al cargar la orden: " + e.getMessage());
            return "redirect:/inicio";
        }
    }
}