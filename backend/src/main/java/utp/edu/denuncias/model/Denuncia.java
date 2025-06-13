package utp.edu.denuncias.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidad JPA que representa una denuncia en el sistema.
 * Mapea a una tabla en la base de datos que almacena información sobre denuncias realizadas por usuarios.
 */
@Data  // Genera automáticamente getters, setters, equals, hashCode y toString
@Entity  // Marca esta clase como una entidad JPA (persistente)
@NoArgsConstructor  // Genera un constructor sin argumentos
@AllArgsConstructor  // Genera un constructor con todos los argumentos
@Builder  // Habilita el patrón builder para crear instancias de esta clase
public class Denuncia {

    /**
     * Identificador único de la denuncia.
     * Se genera automáticamente (auto incrementable).
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Título de la denuncia.
     */
    private String titulo;

    /**
     * Descripción detallada de la denuncia.
     */
    private String descripcion;

    /**
     * Lugar donde ocurrió el hecho denunciado.
     */
    private String lugar;

    /**
     * Categoría de la denuncia (por ejemplo, violencia, robo, etc.).
     * Guardado como texto simple.
     */
    private String categoria;

    /**
     * Estado actual de la denuncia.
     * Se almacena como un string que representa uno de los valores del enum EstadoDenuncia.
     * Por defecto, la denuncia comienza con estado PENDIENTE.
     */
    @Enumerated(EnumType.STRING)
    private EstadoDenuncia estado = EstadoDenuncia.PENDIENTE;

    /**
     * Fecha y hora en que fue creada la denuncia.
     * Se inicializa con la fecha y hora actuales al momento de crear la instancia.
     */
    private LocalDateTime createdDate = LocalDateTime.now();

    /**
     * Relación muchos a uno con la entidad Usuario.
     * Indica qué usuario realizó la denuncia.
     * Se une por la columna "usuario_id" en la tabla Denuncia.
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    /**
     * Enumeración que define los posibles estados de una denuncia.
     */
    public enum EstadoDenuncia {
        PENDIENTE,   // Denuncia recién creada, aún no procesada
        EN_PROCESO,  // Denuncia que está siendo investigada o atendida
        RESUELTO,    // Denuncia que ha sido solucionada o cerrada satisfactoriamente
        ARCHIVADO,   // Denuncia archivada sin resolución inmediata
        RECHAZADO    // Denuncia que ha sido descartada o invalidada
    }
}