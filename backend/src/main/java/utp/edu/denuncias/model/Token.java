package utp.edu.denuncias.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidad JPA que representa un token de autenticación o sesión para un usuario.
 * Se utiliza para gestionar tokens válidos, revocados o expirados.
 */
@Data  // Genera automáticamente getters, setters, equals, hashCode y toString
@Entity  // Marca la clase como entidad JPA persistente
@NoArgsConstructor  // Constructor sin argumentos
@AllArgsConstructor  // Constructor con todos los argumentos
@Builder  // Permite usar el patrón builder para crear instancias
public class Token {

    /**
     * Identificador único del token.
     * Se genera automáticamente.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Valor único del token (por ejemplo, JWT o cadena aleatoria).
     * Debe ser único en la base de datos.
     */
    @Column(unique = true)
    private String token;

    /**
     * Indica si el token ha sido revocado (anulado antes de expirar).
     */
    private boolean revoked;

    /**
     * Indica si el token ha expirado (fecha de expiración superada).
     */
    private boolean expired;

    /**
     * Relación muchos a uno con la entidad Usuario.
     * Indica a qué usuario pertenece este token.
     * Se une mediante la columna "usuario_id".
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    /**
     * Fecha y hora en que se creó el token.
     * No puede ser nulo.
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * Fecha y hora en que expira el token.
     * No puede ser nulo.
     */
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    /**
     * Método anotado con @PrePersist que se ejecuta antes de que el token
     * sea guardado por primera vez en la base de datos.
     * Asigna la fecha y hora actual a createdAt.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}