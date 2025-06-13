package utp.edu.denuncias.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import utp.edu.denuncias.model.Token;
import utp.edu.denuncias.model.Usuario;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JwtUtil {

    // Valor secreto para firmar los tokens, inyectado desde application.properties o similar
    @Value("${jwt.secret}")
    private String secret;

    // Tiempo de expiración del token en milisegundos, inyectado desde configuración
    @Value("${jwt.expiration}")
    private long expiration;

    // Clave secreta para firmar y validar JWT, derivada a partir del valor 'secret'
    private Key secretKey;

    /**
     * Inicializa la clave secreta (secretKey) a partir del valor 'secret'.
     * Se ejecuta automáticamente tras la construcción del bean para preparar la clave.
     */
    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Genera un token JWT para el usuario dado, incluyendo:
     * - El DNI como sujeto (sub).
     * - El rol del usuario como reclamo adicional ("rol").
     * - La fecha de expiración calculada según el valor de configuración.
     * - Firma el token con la clave secreta usando HS512.
     *
     * Además, retorna un objeto Token con el JWT y metadatos para seguimiento
     * como fechas de creación y expiración, y estado (revocado o no).
     *
     * @param user Usuario para quien se genera el token
     * @return Token con el JWT generado y metadatos asociados
     */
    public Token generateToken(Usuario user) {
        return Token.builder()
                .token(
                        Jwts.builder()
                                .setSubject(user.getDni())               // DNI usado como subject
                                .claim("rol", user.getRol())             // Añade rol como claim
                                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Expiración
                                .signWith(secretKey, SignatureAlgorithm.HS512)  // Firma con HS512
                                .compact()
                )
                .usuario(user)
                .expired(false)      // Estado inicial no expirado
                .revoked(false)      // Estado inicial no revocado
                .createdAt(LocalDateTime.now())  // Fecha creación token
                .expiresAt(LocalDateTime.now().plusSeconds(expiration / 1000)) // Fecha expiración
                .build();
    }

    /**
     * Extrae el nombre de usuario (en este caso el DNI) contenido en el JWT.
     * Verifica la firma del token con la clave secreta y retorna el subject.
     *
     * @param token JWT a analizar
     * @return DNI o nombre de usuario extraído del token, o null si falla
     */
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)      // Configura la clave para validar
                .build()
                .parseClaimsJws(token)         // Parsea y valida el token JWT
                .getBody()
                .getSubject();                 // Extrae el subject (usuario)
    }

    /**
     * Valida que el token JWT sea correcto en formato y firma.
     * Si el token no es válido o ha sido alterado, atrapa la excepción y retorna false.
     *
     * @param token JWT a validar
     * @return true si el token es válido, false si no
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);  // Intentar parsear para validar firma
            return true;
        } catch (Exception e) {
            return false;   // Cualquier excepción indica token inválido
        }
    }

    /**
     * Obtiene el nombre de usuario (username) del contexto de seguridad actual.
     * Si no hay usuario autenticado, retorna null.
     *
     * @return Nombre de usuario autenticado o null
     */
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();  // Retorna username autenticado
        }
        return null;
    }
}