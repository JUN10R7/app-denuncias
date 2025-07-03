package utp.edu.denuncias.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import utp.edu.denuncias.enums.Categoria;
import utp.edu.denuncias.enums.Estado;

import java.time.LocalDateTime;

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
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.PENDIENTE;

    /**
     * Fecha y hora en que fue creada la denuncia.
     * Se inicializa con la fecha y hora actuales al momento de crear la instancia.
     */
    @Builder.Default
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
     * Relación muchos a uno con la entidad Usuario.
     * Representa el moderador asignado a la denuncia.
     * Se une por la columna "usuario_id" en la tabla Denuncia.
     * Por defecto, no hay moderador asignado (valor inicial es null).
     */
    @Builder.Default
    @ManyToOne
    @JoinColumn(name = "mod_id")
    private Usuario modAsignado = null;

}