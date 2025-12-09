package cafeteria.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cafeteria.domain.Promocion;

public interface PromocionRepository extends JpaRepository<Promocion, Integer> {
    
    // Buscar promociones activas
    List<Promocion> findByActivoTrue();
    
    // Buscar promociones vigentes
    @Query("SELECT p FROM Promocion p WHERE p.activo = true " +
           "AND (p.fechaInicio IS NULL OR p.fechaInicio <= :fecha) " +
           "AND (p.fechaFin IS NULL OR p.fechaFin >= :fecha)")
    List<Promocion> findPromocionesVigentes(@Param("fecha") LocalDate fecha);
}