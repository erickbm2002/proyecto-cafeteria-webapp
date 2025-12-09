// 📁 UBICACIÓN: src/main/java/cafeteria/controller/CarritoController.java

package cafeteria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cafeteria.Services.CarritoService;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    /**
     * Mostrar el carrito de compras
     */
    @GetMapping
    public String verCarrito(Model model) {
        model.addAttribute("items", carritoService.getItems());
        model.addAttribute("cantidadTotal", carritoService.getCantidadTotal());
        model.addAttribute("total", carritoService.getTotal());
        model.addAttribute("totalConDescuentos", carritoService.getTotalConDescuentos());
        model.addAttribute("ahorroTotal", carritoService.getAhorroTotal());
        model.addAttribute("estaVacio", carritoService.estaVacio());
        
        return "pages/carrito";
    }

    /**
     * Agregar producto al carrito
     */
    @PostMapping("/agregar")
    public String agregarProducto(@RequestParam Integer idProducto,
                                   @RequestParam(defaultValue = "1") Integer cantidad,
                                   @RequestParam(required = false) String origen,
                                   RedirectAttributes redirectAttributes) {
        try {
            carritoService.agregarProducto(idProducto, cantidad);
            redirectAttributes.addFlashAttribute("mensaje", "Producto agregado al carrito");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "danger");
        }

        // Redirigir al origen o al menú por defecto
        if (origen != null && !origen.isEmpty()) {
            return "redirect:" + origen;
        }
        return "redirect:/menu";
    }

    /**
     * Actualizar cantidad de un producto en el carrito
     */
    @PostMapping("/actualizar/{idProducto}")
    public String actualizarCantidad(@PathVariable Integer idProducto,
                                      @RequestParam Integer cantidad,
                                      RedirectAttributes redirectAttributes) {
        try {
            carritoService.actualizarCantidad(idProducto, cantidad);
            redirectAttributes.addFlashAttribute("mensaje", "Cantidad actualizada");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "danger");
        }

        return "redirect:/carrito";
    }

    /**
     * Eliminar producto del carrito
     */
    @GetMapping("/eliminar/{idProducto}")
    public String eliminarProducto(@PathVariable Integer idProducto,
                                    RedirectAttributes redirectAttributes) {
        try {
            carritoService.eliminarProducto(idProducto);
            redirectAttributes.addFlashAttribute("mensaje", "Producto eliminado del carrito");
            redirectAttributes.addFlashAttribute("tipo", "info");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "danger");
        }

        return "redirect:/carrito";
    }

    /**
     * Vaciar el carrito completamente
     */
    @PostMapping("/vaciar")
    public String vaciarCarrito(RedirectAttributes redirectAttributes) {
        carritoService.vaciarCarrito();
        redirectAttributes.addFlashAttribute("mensaje", "Carrito vaciado");
        redirectAttributes.addFlashAttribute("tipo", "info");
        return "redirect:/carrito";
    }

    /**
     * Proceder al checkout (redirige a la página de checkout)
     */
    @GetMapping("/checkout")
    public String checkout(RedirectAttributes redirectAttributes) {
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

        // Redirigir al checkout (implementar después)
        return "redirect:/checkout";
    }
}