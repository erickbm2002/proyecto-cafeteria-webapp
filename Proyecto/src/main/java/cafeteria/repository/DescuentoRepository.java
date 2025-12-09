package cafeteria.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cafeteria.domain.Descuento;
import cafeteria.domain.Producto;

public interface DescuentoRepository extends JpaRepository<Descuento, Integer> {
    
    // Buscar descuentos por producto
    List<Descuento> findByProducto(Producto producto);
    
    // Buscar descuentos activos
    List<Descuento> findByActivoTrue();
    
    // Buscar descuento vigente de un producto
    @Query("SELECT d FROM Descuento d WHERE d.producto = :producto " +
           "AND d.activo = true " +
           "AND (d.fechaInicio IS NULL OR d.fechaInicio <= :fecha) " +
           "AND (d.fechaFin IS NULL OR d.fechaFin >= :fecha)")
    Optional<Descuento> findDescuentoVigente(@Param("producto") Producto producto, 
                                             @Param("fecha") LocalDate fecha);
}