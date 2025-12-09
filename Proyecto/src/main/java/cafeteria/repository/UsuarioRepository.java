package cafeteria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cafeteria.domain.Usuario;
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    // Buscar por correo
    Optional<Usuario> findByCorreo(String correo);
    
    // Buscar usuarios activos
    List<Usuario> findByActivoTrue();
    
    // Buscar por rol
    List<Usuario> findByRol(Usuario.Rol rol);
    
    // Verificar si existe un correo
    boolean existsByCorreo(String correo);
    
    // Buscar administradores activos
    @Query("SELECT u FROM Usuario u WHERE u.rol = 'Administrador' AND u.activo = true")
    List<Usuario> findAdministradoresActivos();
}