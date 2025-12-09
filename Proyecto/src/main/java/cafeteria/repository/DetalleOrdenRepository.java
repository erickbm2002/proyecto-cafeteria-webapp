package cafeteria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cafeteria.domain.DetalleOrden;
import cafeteria.domain.Orden;
import cafeteria.domain.Producto;

public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Integer> {
    
    // Buscar detalles por orden
    List<DetalleOrden> findByOrden(Orden orden);
    
    // Buscar detalles por producto
    List<DetalleOrden> findByProducto(Producto producto);
    
    // Obtener productos más vendidos
    @Query("SELECT d.producto, SUM(d.cantidad) as total " +
           "FROM DetalleOrden d " +
           "GROUP BY d.producto " +
           "ORDER BY total DESC")
    List<Object[]> findProductosMasVendidos();
}