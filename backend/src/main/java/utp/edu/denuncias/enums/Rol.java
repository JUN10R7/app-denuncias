package utp.edu.denuncias.enums;

import lombok.Getter;

/**
 * Enumeración que define los diferentes roles que pueden ser asignados a los usuarios
 * dentro del sistema. Cada rol determina el nivel de acceso y las acciones permitidas.
 */
@Getter
public enum Rol {

    /**
     * Rol que representa a un administrador del sistema.
     * Tiene acceso completo a todas las funcionalidades y permisos
     * disponibles, incluyendo la gestión de usuarios, asignaciones
     * y configuraciones generales del sistema.
     */
    ADMIN("Administrador", "Acceso completo al sistema."),
    /**
     * Representa el rol de Moderador dentro del sistema.
     * Este rol permite al usuario revisar y gestionar denuncias,
     * desempeñando funciones clave en la moderación y control
     * de las actividades relacionadas con las denuncias registradas.
     */
    MOD("Moderador", "Puede revisar y gestionar denuncias."),
    /**
     * Representa el rol de usuario estándar dentro del sistema. Los usuarios con este rol
     * pueden registrar denuncias y realizar un seguimiento del progreso de las mismas.
     */
    USER("Usuario", "Puede registrar denuncias y realizar seguimientos.");

    /**
     * Representa el título asociado a un rol dentro del sistema.
     * Este título proporciona una identificación textual y descriptiva de cada rol.
     */
    private final String titulo;
    /**
     * Representa una descripción detallada asociada a un rol en el sistema.
     * Proporciona información adicional sobre las responsabilidades y permisos
     * asignados al rol específico.
     */
    private final String descripcion;

    /**
     * Constructor del enum Rol que inicializa un rol con su título y descripción.
     *
     * @param titulo el título descriptivo del rol
     * @param descripcion una descripción detallada del rol
     */
    Rol(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
    }
}
