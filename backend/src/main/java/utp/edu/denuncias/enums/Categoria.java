package utp.edu.denuncias.enums;

import lombok.Getter;

/**
 * Enumeración que define las distintas categorías que pueden ser asociadas
 * a denuncias dentro del sistema. Cada categoría se identifica mediante un
 * título y una descripción, que brindan un contexto claro sobre el tipo
 * de incidente representado.
 */
@Getter
public enum Categoria {
    /**
     * Representa la categoría "Violencia" dentro del sistema, que abarca
     * agresiones físicas, psicológicas u otras formas de violencia.
     */
    VIOLENCIA("Violencia", "Agresiones físicas, psicológicas u otras formas de violencia."),
    /**
     * Representa la categoría de "Robo" dentro del sistema.
     * Esta categoría abarca incidentes relacionados con la sustracción
     * de bienes o pertenencias de manera ilegal.
     */
    ROBO("Robo", "Sustracción de bienes o pertenencias."),
    /**
     * Categoría que representa la corrupción.
     * Involucra el mal uso del poder o los recursos públicos para beneficios personales,
     * constituyendo una práctica ilegal y antiética.
     */
    CORRUPTION("Corrupción", "Mal uso del poder o recursos públicos para beneficio personal."),
    /**
     * Categoría que hace referencia a actividades relacionadas con el tráfico,
     * consumo o distribución ilegal de sustancias estupefacientes o psicotrópicas.
     */
    DROGAS("Drogas", "Tráfico, consumo o distribución ilegal de sustancias."),
    /**
     * Representa la categoría "Acoso" en el sistema. Esta categoría incluye
     * actos de persecución, intimidación o acoso en cualquier forma.
     */
    ACOSO("Acoso", "Persecución, intimidación o acoso en cualquier forma."),
    /**
     * Representa la categoría de "Contaminación".
     * Se refiere al daño al medio ambiente causado por residuos u otras actividades nocivas.
     */
    CONTAMINATION("Contaminación", "Dañar el medio ambiente con residuos o actividades nocivas."),
    /**
     * Representa la categoría de vandalismo dentro del sistema.
     * Se refiere a la destrucción o daño deliberado a la propiedad.
     */
    VANDALISMO("Vandalismo", "Destrucción o daño deliberado a propiedad."),
    /**
     * Categoría que representa la negligencia. Se define como la falta de acción
     * o descuido por parte de autoridades o entidades en sus responsabilidades.
     */
    NEGLIGENCIA("Negligencia", "Falta de acción o descuido de autoridades o entidades."),
    /**
     * Categoría que representa actos de discriminación.
     * Define cualquier trato injusto, prejuicio o exclusión
     * basado en características como raza, género, religión, entre otras.
     */
    DISCRIMINATION("Discriminación", "Trato injusto por razones de raza, género, religión, etc."),
    /**
     * Representa la categoría "Estafas" en el contexto del sistema.
     * Esta categoría incluye casos de engaños realizados con fines
     * económicos o personales.
     */
    ESTAFAS("Estafas", "Engaños con fines económicos o personales."),
    /**
     * Categoría que representa los ruidos molestos.
     * Se refiere a sonidos intensos o perturbadores que alteran la tranquilidad.
     */
    RUIDOS_MOLESTOS("Ruidos molestos", "Sonidos intensos o molestos que perturban la tranquilidad."),
    /**
     * Categoría que representa la "Trata de personas".
     * Se refiere a la explotación o tráfico de seres humanos.
     */
    TRATA_DE_PERSONAS("Trata de personas", "Explotación o tráfico de seres humanos."),
    /**
     * Constante que representa la categoría de "Extorsión" dentro del sistema
     * de denuncias. Este tipo de denuncia se refiere a la obtención ilegal
     * de bienes o servicios mediante el uso de violencia o amenazas.
     */
    EXTORTION("Extorsión", "Obtención ilegal de bienes o servicios mediante violencia o amenaza.");

    /**
     * Representa el título descriptivo asociado a un elemento de la enumeración.
     * Este título proporciona una identificación breve y clara que define
     * la naturaleza o propósito del elemento en el sistema.
     */
    private final String titulo;
    /**
     * Representa una descripción detallada asociada a una categoría.
     * Proporciona información adicional acerca de la naturaleza, propósito
     * o alcance de la categoría en el sistema.
     */
    private final String description;

    /**
     * Constructor de la enumeración Categoria que inicializa una categoría con su título y descripción.
     *
     * @param titulo el título descriptivo de la categoría
     * @param descripcion una descripción detallada de la categoría
     */
    Categoria(String titulo, String descripcion) {
        this.titulo = titulo;
        this.description = descripcion;
    }
}
