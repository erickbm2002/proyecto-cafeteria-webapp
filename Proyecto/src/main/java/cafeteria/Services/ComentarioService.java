package cafeteria.Services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cafeteria.domain.Comentario;
import cafeteria.repository.ComentarioRepository;

@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;

    public ComentarioService(ComentarioRepository comentarioRepository) {
        this.comentarioRepository = comentarioRepository;
    }

    @Transactional(readOnly = true)
    public List<Comentario> getAllComentarios() {
        return comentarioRepository.findByOrderByFechaComentarioDesc();
    }

    @Transactional(readOnly = true)
    public Comentario getComentarioById(Integer id) {
        return comentarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Comentario no encontrado con ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Comentario> getComentariosByCorreo(String correo) {
        return comentarioRepository.findByCorreo(correo);
    }

    @Transactional(readOnly = true)
    public List<Comentario> getComentariosByFechas(LocalDateTime inicio, LocalDateTime fin) {
        return comentarioRepository.findByFechaComentarioBetween(inicio, fin);
    }

    @Transactional(readOnly = true)
    public List<Comentario> getUltimosComentarios() {
        return comentarioRepository.findUltimosComentarios();
    }

    @Transactional
    public Comentario save(Comentario comentario) {
        // Validar campos requeridos
        if (comentario.getNombre() == null || comentario.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre es requerido");
        }
        if (comentario.getCorreo() == null || comentario.getCorreo().trim().isEmpty()) {
            throw new RuntimeException("El correo es requerido");
        }
        if (comentario.getComentario() == null || comentario.getComentario().trim().isEmpty()) {
            throw new RuntimeException("El comentario es requerido");
        }

        // Establecer fecha si no existe
        if (comentario.getFechaComentario() == null) {
            comentario.setFechaComentario(LocalDateTime.now());
        }

        return comentarioRepository.save(comentario);
    }

    @Transactional
    public void delete(Integer id) {
        if (!comentarioRepository.existsById(id)) {
            throw new RuntimeException("Comentario no encontrado con ID: " + id);
        }
        comentarioRepository.deleteById(id);
    }
}