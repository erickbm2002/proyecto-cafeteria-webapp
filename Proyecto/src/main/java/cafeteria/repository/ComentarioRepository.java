package cafeteria.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cafeteria.domain.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
    
    // Buscar comentarios recientes
    List<Comentario> findByOrderByFechaComentarioDesc();
    
    // Buscar por correo
    List<Comentario> findByCorreo(String correo);
    
    // Buscar comentarios por rango de fechas
    List<Comentario> findByFechaComentarioBetween(LocalDateTime inicio, LocalDateTime fin);
    
    // Obtener últimos N comentarios
    @Query("SELECT c FROM Comentario c ORDER BY c.fechaComentario DESC")
    List<Comentario> findUltimosComentarios();
}