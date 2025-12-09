package cafeteria.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cafeteria.domain.Factura;
import cafeteria.domain.Transaccion;

public interface TransaccionRepository extends JpaRepository<Transaccion, Integer> {
    
    // Buscar por factura
    List<Transaccion> findByFactura(Factura factura);
    
    // Buscar por estado
    List<Transaccion> findByEstado(Transaccion.Estado estado);
    
    // Buscar por método de pago
    List<Transaccion> findByMetodoPago(Transaccion.MetodoPago metodoPago);
    
    // Buscar transacciones exitosas por periodo
    @Query("SELECT t FROM Transaccion t WHERE t.estado = 'Exitosa' " +
           "AND t.fechaTransaccion BETWEEN :inicio AND :fin")
    List<Transaccion> findTransaccionesExitosas(@Param("inicio") LocalDateTime inicio, 
                                                 @Param("fin") LocalDateTime fin);
    
    // Contar transacciones por método de pago
    long countByMetodoPago(Transaccion.MetodoPago metodoPago);
}