package utp.edu.denuncias.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import utp.edu.denuncias.repository.TokenRepository;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class JwtFilter implements Filter {

    // Utilidad para manejar operaciones con JWT (validar, extraer info, etc.)
    private final JwtUtil jwtUtil;

    // Servicio para cargar detalles del usuario basado en username
    private final UserDetailsServiceImpl userDetailsService;

    // Repositorio para consultar tokens en base de datos (por ejemplo, para verificar revocación o expiración)
    private final TokenRepository tokenRepository;

    /**
     * Método que intercepta cada solicitud HTTP entrante para validar el token JWT.
     *
     * Pasos:
     * 1. Obtiene el encabezado "Authorization" de la solicitud HTTP.
     * 2. Verifica que el encabezado no sea nulo y que comience con "Bearer ".
     * 3. Extrae el token JWT (quitando el prefijo "Bearer ").
     * 4. Consulta en la base de datos para validar que el token no esté expirado ni revocado.
     * 5. Usa JwtUtil para validar la firma y estructura del token.
     * 6. Si el token es válido, extrae el nombre de usuario y carga sus detalles.
     * 7. Crea un objeto de autenticación y lo establece en el contexto de seguridad de Spring.
     * 8. Finalmente, deja pasar la solicitud al siguiente filtro o controlador.
     *
     * @param request  Solicitud entrante
     * @param response Respuesta HTTP
     * @param filterChain Cadena de filtros para continuar el flujo
     * @throws IOException Excepción de IO si ocurre algún error
     * @throws ServletException Excepción de Servlet si falla el procesamiento
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        // Cast para obtener la solicitud HTTP
        HttpServletRequest req = (HttpServletRequest) request;

        // Obtiene el header "Authorization"
        String auth = req.getHeader("Authorization");

        // Log para ver el valor del header Authorization (útil para debugging)
        System.out.println("Header Authorization: " + auth);

        // Verifica que el header exista y comience con "Bearer "
        if (auth != null && auth.startsWith("Bearer ")) {
            // Extrae el token JWT (sin el prefijo "Bearer ")
            String token = auth.substring(7);

            // Consulta la base para verificar que el token no esté expirado, revocado y su fecha de expiración sea futura
            boolean isTokenValid = tokenRepository.findByToken(token)
                    .map(tokenEntity ->
                            !tokenEntity.isExpired() &&
                                    !tokenEntity.isRevoked() &&
                                    tokenEntity.getExpiresAt().isAfter(LocalDateTime.now())
                    ).orElse(false);

            // Valida la estructura y firma del token JWT con JwtUtil
            if (isTokenValid && jwtUtil.validateToken(token)) {
                // Extrae el nombre de usuario del token
                String username = jwtUtil.extractUsername(token);

                // Log para confirmar usuario y token válido
                System.out.println("Token válido para usuario: " + username);

                // Carga los detalles del usuario para la autenticación
                var userDetails = userDetailsService.loadUserByUsername(username);

                // Crea un objeto Authentication con los detalles del usuario y sus roles
                var authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // Añade detalles adicionales de la solicitud a la autenticación
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

                // Establece la autenticación en el contexto de seguridad de Spring
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continúa la cadena de filtros, pasando la solicitud y respuesta
        filterChain.doFilter(request, response);
    }
}