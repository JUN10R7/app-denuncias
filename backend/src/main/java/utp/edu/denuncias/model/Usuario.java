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

    /**
     * Relación uno a muchos con la entidad Denuncia.
     * Representa las denuncias asignadas a un moderador del sistema.
     * Este atributo almacena las denuncias en las que el moderador actúa como encargado.
     */
    @OneToMany(mappedBy = "modAsignado")
    private List<Denuncia> denunciasAsignadas;

    /**
     * Lista de solicitudes asignadas al usuario como revisor.
     *
     * Representa una relación uno a muchos con la entidad Solicitud,
     * donde un usuario desempeña el rol de revisor de dichas solicitudes.
     * El atributo "mappedBy" indica que la relación está mapeada por el campo "revisor"
     * en la entidad Solicitud.
     */
    @OneToMany(mappedBy = "revisor")
    private List<Solicitud> solicitudesRecibidas;

    /**
     * Lista de solicitudes enviadas por el usuario actual.
     * Representa una relación uno a muchos con la entidad Solicitud,
     * donde este usuario actúa como autor de las solicitudes.
     */
    @OneToMany(mappedBy = "autor")
    private List<Solicitud> solicitudesEnviadas;
}