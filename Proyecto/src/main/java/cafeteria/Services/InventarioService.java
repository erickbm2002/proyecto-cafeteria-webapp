package cafeteria.Services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cafeteria.domain.Inventario;
import cafeteria.domain.Producto;
import cafeteria.repository.InventarioRepository;
import cafeteria.repository.ProductoRepository;

@Service
public class InventarioService {

    private final InventarioRepository inventarioRepository;
    
    @Autowired
    private ProductoRepository productoRepository;

    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    @Transactional(readOnly = true)
    public List<Inventario> getAllInventarios() {
        return inventarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Inventario getInventarioById(Integer id) {
        return inventarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Inventario no encontrado con ID: " + id));
    }

    @Transactional(readOnly = true)
    public Inventario getInventarioByProducto(Producto producto) {
        return inventarioRepository.findByProducto(producto)
            .orElse(null);
    }

    @Transactional(readOnly = true)
    public Inventario getInventarioByProductoId(Integer idProducto) {
        Producto producto = productoRepository.findById(idProducto)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + idProducto));
        return getInventarioByProducto(producto);
    }

    @Transactional(readOnly = true)
    public List<Inventario> getProductosStockBajo(int umbral) {
        return inventarioRepository.findProductosStockBajo(umbral);
    }

    @Transactional(readOnly = true)
    public List<Inventario> getProductosSinStock() {
        return inventarioRepository.findByCantidadActual(0);
    }

    @Transactional
    public Inventario crearInventario(Producto producto, Integer cantidadInicial) {
        // Verificar que no exista ya un inventario para este producto
        if (inventarioRepository.findByProducto(producto).isPresent()) {
            throw new RuntimeException("Ya existe un inventario para el producto: " + producto.getNombre());
        }

        Inventario inventario = new Inventario();
        inventario.setProducto(producto);
        inventario.setCantidadActual(cantidadInicial != null ? cantidadInicial : 0);
        inventario.setFechaActualizacion(LocalDateTime.now());
        
        return inventarioRepository.save(inventario);
    }

    @Transactional
    public Inventario actualizarCantidad(Integer idProducto, Integer nuevaCantidad) {
        if (nuevaCantidad < 0) {
            throw new RuntimeException("La cantidad no puede ser negativa");
        }

        Inventario inventario = getInventarioByProductoId(idProducto);
        if (inventario == null) {
            // Si no existe inventario, crear uno nuevo
            Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + idProducto));
            return crearInventario(producto, nuevaCantidad);
        }

        inventario.setCantidadActual(nuevaCantidad);
        inventario.setFechaActualizacion(LocalDateTime.now());
        return inventarioRepository.save(inventario);
    }

    @Transactional
    public Inventario incrementarStock(Integer idProducto, Integer cantidad) {
        if (cantidad <= 0) {
            throw new RuntimeException("La cantidad a incrementar debe ser mayor a 0");
        }

        Inventario inventario = getInventarioByProductoId(idProducto);
        if (inventario == null) {
            Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + idProducto));
            return crearInventario(producto, cantidad);
        }

        inventario.incrementarStock(cantidad);
        return inventarioRepository.save(inventario);
    }

    @Transactional
    public Inventario decrementarStock(Integer idProducto, Integer cantidad) {
        if (cantidad <= 0) {
            throw new RuntimeException("La cantidad a decrementar debe ser mayor a 0");
        }

        Inventario inventario = getInventarioByProductoId(idProducto);
        if (inventario == null) {
            throw new RuntimeException("No existe inventario para el producto con ID: " + idProducto);
        }

        inventario.decrementarStock(cantidad);
        return inventarioRepository.save(inventario);
    }

    @Transactional
    public void eliminarInventario(Integer id) {
        if (!inventarioRepository.existsById(id)) {
            throw new RuntimeException("Inventario no encontrado con ID: " + id);
        }
        inventarioRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean verificarDisponibilidad(Integer idProducto, Integer cantidadRequerida) {
        Inventario inventario = getInventarioByProductoId(idProducto);
        return inventario != null && inventario.isDisponible(cantidadRequerida);
    }
}