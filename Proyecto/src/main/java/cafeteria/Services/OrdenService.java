package cafeteria.Services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cafeteria.domain.DetalleOrden;
import cafeteria.domain.Orden;
import cafeteria.domain.Usuario;
import cafeteria.repository.OrdenRepository;

@Service
public class OrdenService {

    private final OrdenRepository ordenRepository;
    
    @Autowired
    private InventarioService inventarioService;

    public OrdenService(OrdenRepository ordenRepository) {
        this.ordenRepository = ordenRepository;
    }

    @Transactional(readOnly = true)
    public List<Orden> getAllOrdenes() {
        return ordenRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Orden getOrdenById(Integer id) {
        return ordenRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Orden> getOrdenesByUsuario(Usuario usuario) {
        return ordenRepository.findByUsuario(usuario);
    }

    @Transactional(readOnly = true)
    public List<Orden> getOrdenesByEstado(Orden.Estado estado) {
        return ordenRepository.findByEstado(estado);
    }

    @Transactional(readOnly = true)
    public List<Orden> getOrdenesRecientes() {
        LocalDate hace30Dias = LocalDate.now().minusDays(30);
        return ordenRepository.findOrdenesRecientes(hace30Dias);
    }

    @Transactional(readOnly = true)
    public List<Orden> getOrdenesByFechas(LocalDate inicio, LocalDate fin) {
        return ordenRepository.findByFechaOrdenBetween(inicio, fin);
    }

    @Transactional
    public Orden crearOrden(Usuario usuario, List<DetalleOrden> detalles) {
        // Validar que hay detalles
        if (detalles == null || detalles.isEmpty()) {
            throw new RuntimeException("La orden debe tener al menos un producto");
        }

        // Verificar disponibilidad de inventario para todos los productos
        for (DetalleOrden detalle : detalles) {
            if (!detalle.getProducto().isDisponible(detalle.getCantidad())) {
                throw new RuntimeException("Stock insuficiente para el producto: " + 
                                         detalle.getProducto().getNombre());
            }
        }

        // Crear la orden
        Orden orden = new Orden();
        orden.setUsuario(usuario);
        orden.setFechaOrden(LocalDate.now());
        orden.setEstado(Orden.Estado.Pendiente);
        
        // Los campos de fecha se manejan automáticamente por la BD

        // Calcular el total y asociar detalles
        BigDecimal total = BigDecimal.ZERO;
        for (DetalleOrden detalle : detalles) {
            detalle.setOrden(orden);
            total = total.add(detalle.getSubtotal());
        }
        orden.setTotal(total);
        orden.setDetalles(detalles);

        // Guardar la orden
        Orden ordenGuardada = ordenRepository.save(orden);

        // Decrementar el inventario
        for (DetalleOrden detalle : detalles) {
            inventarioService.decrementarStock(detalle.getProducto().getIdProducto(), 
                                              detalle.getCantidad());
        }

        return ordenGuardada;
    }

    @Transactional
    public Orden cambiarEstado(Integer idOrden, Orden.Estado nuevoEstado) {
        Orden orden = getOrdenById(idOrden);
        
        // Si se cancela la orden, restaurar el inventario
        if (nuevoEstado == Orden.Estado.Cancelada && orden.getEstado() != Orden.Estado.Cancelada) {
            for (DetalleOrden detalle : orden.getDetalles()) {
                inventarioService.incrementarStock(detalle.getProducto().getIdProducto(), 
                                                   detalle.getCantidad());
            }
        }
        
        orden.setEstado(nuevoEstado);
        return ordenRepository.save(orden);
    }

    @Transactional
    public void cancelarOrden(Integer idOrden) {
        cambiarEstado(idOrden, Orden.Estado.Cancelada);
    }

    @Transactional(readOnly = true)
    public long contarOrdenesPorEstado(Orden.Estado estado) {
        return ordenRepository.countByEstado(estado);
    }

    @Transactional(readOnly = true)
    public BigDecimal calcularTotalVentas(LocalDate inicio, LocalDate fin) {
        List<Orden> ordenes = ordenRepository.findByFechaOrdenBetween(inicio, fin);
        return ordenes.stream()
            .filter(o -> o.getEstado() != Orden.Estado.Cancelada)
            .map(Orden::getTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}