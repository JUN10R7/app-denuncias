package utp.edu.denuncias.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import utp.edu.denuncias.enums.Categoria;
import utp.edu.denuncias.enums.Estado;

import java.time.LocalDateTime;

/**
 * Entidad JPA que representa una denuncia en el sistema.
 * Mapea a una tabla en la base de datos que almacena información sobre denuncias realizadas por usuarios.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private String description;

    /**
     * Lugar donde ocurrió el hecho denunciado.
     */
    private String lugar;

    /**
     * Categoría de la denuncia (por ejemplo, violencia, robo, etc.).
     * Guardado como texto simple.
     */
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    /**
     * Estado actual de la denuncia.
     * Se almacena como un string que representa uno de los valores del enum EstadoDenuncia.
     * Por defecto, la denuncia comienza con estado PENDIENTE.
     */
    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.PENDIENTE;

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

}