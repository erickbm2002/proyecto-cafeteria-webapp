// 📁 UBICACIÓN: src/main/java/cafeteria/Services/CarritoService.java

package cafeteria.Services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import cafeteria.domain.ItemCarrito;
import cafeteria.domain.Producto;
import cafeteria.repository.ProductoRepository;

/**
 * Servicio para manejar el carrito de compras
 * @SessionScope mantiene una instancia única por sesión de usuario
 */
@Service
@SessionScope
public class CarritoService {

    @Autowired
    private ProductoRepository productoRepository;

    // Lista de items en el carrito (única por sesión)
    private List<ItemCarrito> items = new ArrayList<>();

    /**
     * Agregar un producto al carrito
     */
    public void agregarProducto(Integer idProducto, Integer cantidad) {
        // Validar cantidad
        if (cantidad == null || cantidad <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a 0");
        }

        // Buscar el producto
        Producto producto = productoRepository.findById(idProducto)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + idProducto));

        // Verificar que el producto esté activo
        if (!producto.getActivo()) {
            throw new RuntimeException("El producto no está disponible");
        }

        // Verificar disponibilidad en inventario
        if (!producto.isDisponible(cantidad)) {
            throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
        }

        // Verificar si el producto ya está en el carrito
        ItemCarrito itemExistente = buscarItemPorId(idProducto);
        
        if (itemExistente != null) {
            // Si ya existe, actualizar la cantidad
            int nuevaCantidad = itemExistente.getCantidad() + cantidad;
            
            // Verificar disponibilidad con la nueva cantidad
            if (!producto.isDisponible(nuevaCantidad)) {
                throw new RuntimeException("Stock insuficiente. Disponible: " + 
                    (producto.getInventario() != null ? producto.getInventario().getCantidadActual() : 0));
            }
            
            itemExistente.setCantidad(nuevaCantidad);
        } else {
            // Si no existe, agregar nuevo item
            ItemCarrito nuevoItem = new ItemCarrito(producto, cantidad);
            items.add(nuevoItem);
        }
    }

    /**
     * Actualizar la cantidad de un item en el carrito
     */
    public void actualizarCantidad(Integer idProducto, Integer nuevaCantidad) {
        if (nuevaCantidad == null || nuevaCantidad <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a 0");
        }

        ItemCarrito item = buscarItemPorId(idProducto);
        if (item == null) {
            throw new RuntimeException("El producto no está en el carrito");
        }

        // Verificar disponibilidad
        Producto producto = productoRepository.findById(idProducto)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (!producto.isDisponible(nuevaCantidad)) {
            throw new RuntimeException("Stock insuficiente. Disponible: " + 
                (producto.getInventario() != null ? producto.getInventario().getCantidadActual() : 0));
        }

        item.setCantidad(nuevaCantidad);
    }

    /**
     * Eliminar un producto del carrito
     */
    public void eliminarProducto(Integer idProducto) {
        items.removeIf(item -> item.getIdProducto().equals(idProducto));
    }

    /**
     * Vaciar el carrito completamente
     */
    public void vaciarCarrito() {
        items.clear();
    }

    /**
     * Obtener todos los items del carrito
     */
    public List<ItemCarrito> getItems() {
        return new ArrayList<>(items);
    }

    /**
     * Obtener la cantidad total de items en el carrito
     */
    public int getCantidadTotal() {
        return items.stream()
            .mapToInt(ItemCarrito::getCantidad)
            .sum();
    }

    /**
     * Calcular el total del carrito (sin descuentos)
     */
    public BigDecimal getTotal() {
        return items.stream()
            .map(item -> item.getPrecio().multiply(new BigDecimal(item.getCantidad())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Calcular el total con descuentos aplicados
     */
    public BigDecimal getTotalConDescuentos() {
        return items.stream()
            .map(ItemCarrito::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Calcular el ahorro total por descuentos
     */
    public BigDecimal getAhorroTotal() {
        return items.stream()
            .map(ItemCarrito::getAhorro)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Verificar si el carrito está vacío
     */
    public boolean estaVacio() {
        return items.isEmpty();
    }

    /**
     * Buscar un item por ID de producto
     */
    private ItemCarrito buscarItemPorId(Integer idProducto) {
        return items.stream()
            .filter(item -> item.getIdProducto().equals(idProducto))
            .findFirst()
            .orElse(null);
    }

    /**
     * Verificar disponibilidad de todos los productos en el carrito
     */
    public boolean verificarDisponibilidad() {
        for (ItemCarrito item : items) {
            Producto producto = productoRepository.findById(item.getIdProducto()).orElse(null);
            if (producto == null || !producto.getActivo() || !producto.isDisponible(item.getCantidad())) {
                return false;
            }
        }
        return true;
    }
}