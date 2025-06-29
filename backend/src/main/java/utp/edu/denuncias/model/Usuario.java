package utp.edu.denuncias.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import utp.edu.denuncias.enums.Rol;

import java.util.List;

/**
 * Entidad JPA que representa un usuario del sistema.
 * Contiene información personal, credenciales y relaciones con denuncias y tokens.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    /**
     * Identificador único del usuario.
     * Se genera automáticamente.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Nombres del usuario.
     */
    private String nombres;

    /**
     * Apellidos del usuario.
     */
    private String apellidos;

    /**
     * Nombre de usuario (login).
     * Debe ser único y no puede ser nulo.
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * Contraseña del usuario.
     */
    private String password;

    /**
     * Documento Nacional de Identidad (DNI).
     * Debe ser único y no puede ser nulo.
     */
    @Column(unique = true, nullable = false)
    private String dni;

    /**
     * Correo electrónico del usuario.
     * Debe ser único y no puede ser nulo.
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Rol del usuario en el sistema.
     * Se almacena como texto correspondiente al enum Rol.
     */
    @Enumerated(EnumType.STRING)
    private Rol rol;

    /**
     * Indica si el usuario está habilitado o no en el sistema.
     * Este atributo puede ser utilizado para controlar el acceso de un usuario
     * sin necesidad de eliminarlo del registro de la base de datos.
     * Por defecto, el valor se inicializa en true, habilitando al usuario.
     */
    @Builder.Default
    private boolean enabled = true;

    /**
     * Relación uno a muchos con la entidad Denuncia.
     * Un usuario puede tener múltiples denuncias.
     * Cascade ALL asegura que operaciones sobre Usuario se propaguen a sus denuncias.
     */
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Denuncia> denuncias;

}