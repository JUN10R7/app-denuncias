package utp.edu.denuncias.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

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
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/registro","/auth/login", "/api/enum/**").permitAll()
                        .requestMatchers("/api/usuario/**","/api/denuncia/usuario/**","/usuario/**").hasAnyRole("USER", "MOD", "ADMIN")
                        .requestMatchers("/api/usuario/mod/**","/api/denuncia/mod/**","/api/solicitud/**","/mod/**").hasAnyRole("MOD", "ADMIN")
                        .requestMatchers("/api/usuario/admin/**","/api/denuncia/admin/**","/api/solicitud/admin/**","/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .userDetailsService(userDetailsService)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler()))
                .build();
    }

    /**
     * Configura y proporciona una fuente de configuración CORS para la aplicación.
     * Define los patrones de origen permitidos, métodos HTTP soportados, encabezados aceptados,
     * y habilita el uso de credenciales en las solicitudes CORS.
     *
     * @return una instancia de {@link CorsConfigurationSource} que contiene la configuración CORS definida
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /**
     * Proporciona un bean de tipo {@link AccessDeniedHandler} encargado de manejar los casos
     * en los que un usuario intenta acceder a un recurso para el cual no tiene permisos.
     * Establece un estado HTTP 403 (Forbidden) y devuelve una respuesta en formato JSON
     * que contiene detalles sobre el error.
     *
     * @return una instancia configurada de {@link AccessDeniedHandler} que gestiona las respuestas
     *         en casos de acceso denegado.
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return ( _, response, _) -> {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            response.getWriter().write("""
            {
                "error": "Acceso denegado",
                "message": "No tienes permisos para acceder a este recurso.",
                "status": 403
            }
        """);
        };
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