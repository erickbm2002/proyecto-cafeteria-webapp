package cafeteria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cafeteria.domain.Inventario;
import cafeteria.domain.Producto;

public interface InventarioRepository extends JpaRepository<Inventario, Integer> {
    
    // Buscar por producto
    Optional<Inventario> findByProducto(Producto producto);
    
    // Buscar productos con stock bajo
    @Query("SELECT i FROM Inventario i WHERE i.cantidadActual <= :cantidad")
    List<Inventario> findProductosStockBajo(int cantidad);
    
    // Buscar productos sin stock
    List<Inventario> findByCantidadActual(int cantidad);
}