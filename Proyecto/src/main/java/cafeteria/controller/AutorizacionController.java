package cafeteria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cafeteria.Services.UsuarioService;
import cafeteria.domain.Usuario;
import jakarta.validation.Valid;

@Controller
public class AutorizacionController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                       @RequestParam(value = "logout", required = false) String logout,
                       Model model) {
        
        if (error != null) {
            model.addAttribute("error", "Correo o contraseña incorrectos");
        }
        
        if (logout != null) {
            model.addAttribute("mensaje", "Has cerrado sesión exitosamente");
        }
        
        return "pages/auth/login";
    }

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "pages/auth/registro";
    }

    @PostMapping("/registro/guardar")
    public String registrarUsuario(@Valid @ModelAttribute Usuario usuario,
                                    BindingResult result,
                                    @RequestParam("confirmPassword") String confirmPassword,
                                    RedirectAttributes redirectAttributes,
                                    Model model) {
        
        // Validar errores de validación
        if (result.hasErrors()) {
            model.addAttribute("error", "Por favor corrige los errores en el formulario");
            return "pages/auth/registro";
        }

        // Validar que las contraseñas coincidan
        if (!usuario.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "pages/auth/registro";
        }

        // Validar longitud mínima de contraseña
        if (usuario.getPassword().length() < 6) {
            model.addAttribute("error", "La contraseña debe tener al menos 6 caracteres");
            return "pages/auth/registro";
        }

        // Verificar si el correo ya existe
        if (usuarioService.existeCorreo(usuario.getCorreo())) {
            model.addAttribute("error", "El correo ya está registrado");
            return "pages/auth/registro";
        }

        try {

            // Establecer rol por defecto como Cliente
            usuario.setRol(Usuario.Rol.Cliente);
            usuario.setActivo(true);
            
            usuarioService.save(usuario);
            
            redirectAttributes.addFlashAttribute("mensaje", "¡Registro exitoso! Ya puedes iniciar sesión");
            return "redirect:/login";
            
        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar usuario: " + e.getMessage());
            return "pages/auth/registro";
        }
    }

    @GetMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "pages/auth/acceso-denegado";
    }
}