package utp.edu.denuncias.enums;

/**
 * Enumeración que define los roles posibles que puede tener un usuario en el sistema.
 * Estos roles generalmente se usan para controlar permisos y accesos.
 */
public enum Rol {
    /**
     * Rol de administrador con permisos completos.
     */
    ADMIN,

    /**
     * Rol de usuario estándar con permisos limitados.
     */
    USER,

    /**
     * Rol de moderador con permisos intermedios, generalmente para supervisar contenidos o usuarios.
     */
    MODERATOR
}