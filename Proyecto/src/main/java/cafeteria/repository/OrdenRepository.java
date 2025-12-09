package cafeteria.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cafeteria.domain.Orden;
import cafeteria.domain.Usuario;

public interface OrdenRepository extends JpaRepository<Orden, Integer> {
    
    // Buscar órdenes por usuario
    List<Orden> findByUsuario(Usuario usuario);
    
    // Buscar por estado
    List<Orden> findByEstado(Orden.Estado estado);
    
    // Buscar órdenes por rango de fechas
    List<Orden> findByFechaOrdenBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    // Buscar órdenes de un usuario por estado
    List<Orden> findByUsuarioAndEstado(Usuario usuario, Orden.Estado estado);
    
    // Contar órdenes por estado
    long countByEstado(Orden.Estado estado);
    
    // Obtener órdenes recientes (últimos 30 días)
    @Query("SELECT o FROM Orden o WHERE o.fechaOrden >= :fecha ORDER BY o.fechaOrden DESC")
    List<Orden> findOrdenesRecientes(@Param("fecha") LocalDate fecha);
}
