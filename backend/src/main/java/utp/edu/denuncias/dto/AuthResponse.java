package utp.edu.denuncias.dto;

import lombok.Value;

/**
 * Clase DTO que representa la respuesta de autenticación.
 * Contiene el token generado después de un login exitoso.
 */
@Value  // Anotación de Lombok que hace esta clase inmutable, genera getters, constructor, equals, hashCode y toString automáticamente
public class AuthResponse {
    /**
     * Token de autenticación JWT u otro tipo de token que se devuelve al usuario.
     */
    String token;
}