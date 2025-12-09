package cafeteria.Services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cafeteria.domain.Usuario;
import cafeteria.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Usuario> getUsuariosActivos() {
        return usuarioRepository.findByActivoTrue();
    }

    @Transactional(readOnly = true)
    public Usuario getUsuarioById(Integer id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    @Transactional(readOnly = true)
    public Usuario getUsuarioByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con correo: " + correo));
    }

    @Transactional(readOnly = true)
    public List<Usuario> getUsuariosByRol(Usuario.Rol rol) {
        return usuarioRepository.findByRol(rol);
    }

    @Transactional
    public Usuario save(Usuario usuario) {
        // Validar que el correo no esté duplicado (excepto en actualización)
        if (usuario.getIdUsuario() == null) {
            if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
                throw new RuntimeException("Ya existe un usuario con el correo: " + usuario.getCorreo());
            }
        }
        
        // Los campos de fecha se manejan automáticamente por la BD
        // No es necesario setearlos manualmente aquí
        
        // Establecer valores por defecto
        if (usuario.getActivo() == null) {
            usuario.setActivo(true);
        }
        if (usuario.getRol() == null) {
            usuario.setRol(Usuario.Rol.Cliente);
        }
        
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void delete(Integer id) {
        Usuario usuario = getUsuarioById(id);
        
        // Verificar si tiene órdenes asociadas antes de eliminar
        if (usuario.getOrdenes() != null && !usuario.getOrdenes().isEmpty()) {
            throw new RuntimeException("No se puede eliminar el usuario porque tiene órdenes asociadas. " +
                                     "Considere desactivarlo en lugar de eliminarlo.");
        }
        
        usuarioRepository.deleteById(id);
    }

    @Transactional
    public void toggleActivo(Integer id) {
        Usuario usuario = getUsuarioById(id);
        usuario.setActivo(!usuario.getActivo());
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void cambiarRol(Integer id, Usuario.Rol nuevoRol) {
        Usuario usuario = getUsuarioById(id);
        usuario.setRol(nuevoRol);
        usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public boolean existeCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

    @Transactional(readOnly = true)
    public List<Usuario> getAdministradores() {
        return usuarioRepository.findAdministradoresActivos();
    }
}