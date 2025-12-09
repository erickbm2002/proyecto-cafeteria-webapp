package cafeteria.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // Rutas públicas - accesibles sin login
                .requestMatchers("/", "/inicio", "/menu", "/contacto", "/galeria").permitAll()
                .requestMatchers("/contacto/enviar").permitAll()
                .requestMatchers("/registro", "/registro/guardar").permitAll()
                .requestMatchers("/login", "/logout").permitAll()
                .requestMatchers("/error").permitAll()
                
                // Carrito - público para facilitar compras
                .requestMatchers("/carrito/**").permitAll()
                
                // Recursos estáticos públicos
                .requestMatchers("/css/**", "/js/**", "/img/**", "/webjars/**").permitAll()
                .requestMatchers("/favicon.ico").permitAll()
                
                // Rutas de administrador - solo para usuarios con rol Administrador
                .requestMatchers("/admin/**").hasAuthority("Administrador")
                
                // Rutas de cliente - para usuarios autenticados (Cliente o Administrador)
                .requestMatchers("/mis-pedidos/**", "/perfil/**", "/checkout/**").authenticated()
                
                // Cualquier otra ruta requiere autenticación
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/inicio", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/inicio?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .exceptionHandling(exception -> exception
                .accessDeniedPage("/acceso-denegado")
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/carrito/**") // Deshabilitar CSRF solo para carrito si es necesario
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Sin encriptación - contraseñas en texto plano
        return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}