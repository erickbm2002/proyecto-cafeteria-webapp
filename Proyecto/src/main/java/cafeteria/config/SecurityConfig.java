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


                        .requestMatchers("/carrito", "/carrito/**").authenticated()

                        // Recursos estáticos públicos
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/webjars/**").permitAll()
                        .requestMatchers("/favicon.ico").permitAll()

    
                        .requestMatchers("/admin/**").hasAuthority("Administrador")

                        // RUTAS DE CHECKOUT Y PEDIDOS 
                        .requestMatchers("/checkout", "/checkout/**").authenticated()
                        .requestMatchers("/mis-pedidos", "/mis-pedidos/**").authenticated()
                        .requestMatchers("/perfil", "/perfil/**").authenticated()

                        // Cualquier otra ruta requiere autenticación
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/inicio", true)
                        .failureUrl("/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/inicio?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/acceso-denegado"))
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/carrito/**") 
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        /// Quitamos la encriptacion por problemas que teniamos en la base de daytos
        return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}