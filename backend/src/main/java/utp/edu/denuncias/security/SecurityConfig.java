package utp.edu.denuncias.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UserDetailsServiceImpl userDetailsService;

    /**
     * Configura y proporciona un bean de {@link DaoAuthenticationProvider}.
     * Este proveedor es responsable de manejar la autenticación utilizando los detalles del usuario
     * y los mecanismos de codificación de contraseñas.
     *
     * @return una instancia de {@link DaoAuthenticationProvider} configurada con la implementación personalizada
     * de {@code UserDetailsService} y el codificador de contraseñas
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Configura la cadena de filtros de seguridad para manejar las solicitudes HTTP en la aplicación.
     * El método desactiva la protección CSRF, define ciertos patrones de solicitud como accesibles públicamente,
     * aplica autenticación para todas las demás solicitudes, establece una política de gestión de sesión sin estado
     * e integra los filtros necesarios de detalles de usuario y JWT dentro de la cadena de seguridad.
     *
     * @param http el objeto {@link HttpSecurity} utilizado para configurar los ajustes de seguridad HTTP
     * @return una instancia de {@link SecurityFilterChain} con la configuración de seguridad aplicada
     * @throws Exception si ocurre un error durante la configuración
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/registro","/auth/login").permitAll()
                        .requestMatchers("/usuario/**").hasAnyRole("USER", "MOD", "ADMIN")
                        .requestMatchers("/mod/**").hasAnyRole("MOD", "ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .userDetailsService(userDetailsService)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Proporciona un bean de {@link PasswordEncoder} para codificar y validar contraseñas de usuario.
     * Esta implementación utiliza {@link BCryptPasswordEncoder} como mecanismo de codificación.
     *
     * @return una instancia de {@link PasswordEncoder} que codifica contraseñas utilizando el algoritmo BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura y proporciona un bean de {@link AuthenticationManager}.
     * Este método obtiene la instancia de {@link AuthenticationManager}
     * a partir del objeto {@link AuthenticationConfiguration} proporcionado.
     *
     * @param config el objeto {@link AuthenticationConfiguration} utilizado para obtener el administrador de autenticación
     * @return una instancia configurada de {@link AuthenticationManager}
     * @throws Exception si ocurre un error al obtener el administrador de autenticación
     */
    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}