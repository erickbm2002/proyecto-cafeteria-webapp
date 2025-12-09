package cafeteria.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cafeteria.domain.Factura;
import cafeteria.domain.Orden;

public interface FacturaRepository extends JpaRepository<Factura, Integer> {
    
    // Buscar por orden
    Optional<Factura> findByOrden(Orden orden);
    
    // Buscar por estado
    List<Factura> findByEstado(Factura.Estado estado);
    
    // Buscar facturas por rango de fechas
    List<Factura> findByFechaEmisionBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    // Facturas pendientes de pago
    List<Factura> findByEstadoOrderByFechaEmisionDesc(Factura.Estado estado);
    
    // Total facturado en un periodo
    @Query("SELECT SUM(f.montoTotal) FROM Factura f " +
           "WHERE f.estado = 'Pagada' " +
           "AND f.fechaEmision BETWEEN :inicio AND :fin")
    Double calcularTotalFacturado(@Param("inicio") LocalDate inicio, 
                                   @Param("fin") LocalDate fin);
}