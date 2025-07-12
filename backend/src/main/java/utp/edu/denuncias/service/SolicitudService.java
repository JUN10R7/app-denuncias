package utp.edu.denuncias.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import utp.edu.denuncias.dto.SolicitudRequest;
import utp.edu.denuncias.dto.SolicitudResponse;
import utp.edu.denuncias.dto.SolicitudRevisionRequest;
import utp.edu.denuncias.enums.Estado;
import utp.edu.denuncias.enums.Rol;
import utp.edu.denuncias.model.Solicitud;
import utp.edu.denuncias.model.Usuario;
import utp.edu.denuncias.repository.DenunciaRepository;
import utp.edu.denuncias.repository.SolicitudRepository;
import utp.edu.denuncias.repository.UserRepository;
import utp.edu.denuncias.security.JwtUtil;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio encargado de gestionar las operaciones relacionadas con las solicitudes.
 * Proporciona métodos para crear nuevas solicitudes, revisar solicitudes existentes
 * y manipular sus estados en el sistema.
 */
@Service
@RequiredArgsConstructor
public class SolicitudService {

    /**
     * Repositorio para interactuar con la entidad {@link Solicitud} en la base de datos.
     * Proporciona métodos para realizar operaciones básicas de persistencia, tales como
     * guardar, actualizar, eliminar y buscar solicitudes.
     */
    private final SolicitudRepository solicitudRepository;
    /**
     * Repositorio utilizado para interactuar con la entidad {@link Usuario}.
     * Se encarga de realizar operaciones de manipulación y consulta sobre los datos
     * de los usuarios en el sistema.
     */
    private final UserRepository userRepository;
    /**
     * Repositorio utilizado para realizar operaciones relacionadas con la entidad Denuncia.
     * Proporciona acceso a los métodos para consultar, guardar, actualizar y eliminar denuncias
     * en la base de datos.
     */
    private final DenunciaRepository denunciaRepository;

    /**
     * Servicio de notificaciones utilizado para enviar alertas o mensajes
     * relacionados con las operaciones realizadas en el sistema.
     * Este servicio permite informar a los usuarios sobre eventos importantes
     * como cambios en el estado de las solicitudes o resultados de revisiones.
     */
    private final NotificationService notificationService;

    /**
     * Recupera una solicitud específica a partir de su identificador único.
     * Si no se encuentra una solicitud con el ID proporcionado, lanza una excepción.
     *
     * @param id Identificador único de la solicitud.
     * @return una instancia de {@link SolicitudResponse} que representa los datos de la solicitud encontrada.
     * @throws RuntimeException si no existe una solicitud con el ID proporcionado.
     */
    public SolicitudResponse listarSolicitud(Long id) {
        var solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La solicitud con el ID " + id + " no existe"));
        return SolicitudResponse.from(solicitud);
    }

    /**
     * Recupera todas las solicitudes relacionadas con el usuario autenticado cuya
     * propiedad como autor o revisor tiene un estado distinto a RESUELTO o RECHAZADO.
     *
     * @return una lista de objetos {@link SolicitudResponse} que representan las solicitudes
     *         asociadas al usuario autenticado, excluyendo las que se encuentren en estado
     *         RESUELTO o RECHAZADO.
     * @throws RuntimeException si no se puede obtener el usuario autenticado.
     */
    public List<SolicitudResponse> listarSolicitudes() {
        var user = userRepository.findByUsernameAndEnabledTrue(JwtUtil.getCurrentUsername())
                .orElseThrow(() -> new RuntimeException("No se pudo obtener el usuario autenticado"));
        return SolicitudResponse.from(solicitudRepository.findByAutorOrRevisorId(user.getId()));
    }

    /**
     * Lista todas las solicitudes en las que no se ha asignado un revisor,
     * ordenadas por la fecha de creación en orden descendente.
     *
     * @return una lista de objetos {@link SolicitudResponse} que representan las solicitudes sin revisor asignado.
     */
    public List<SolicitudResponse> listarSolicitudesAdmin() {
        return SolicitudResponse.from(solicitudRepository.findByRevisorIsNullOrderByCreatedDateDesc());
    }

    /**
     * Lista las solicitudes asociadas a una denuncia específica, ordenadas por fecha de creación de manera descendente.
     * Si no se encuentra la denuncia con el ID proporcionado, se lanza una excepción.
     *
     * @param id ID de la denuncia cuyos datos serán utilizados para obtener las solicitudes asociadas.
     * @return una lista de objetos {@link SolicitudResponse} que representan las solicitudes asociadas
     *         a la denuncia especificada.
     * @throws RuntimeException si no existe una denuncia con el ID proporcionado.
     */
    public List<SolicitudResponse> listarSolicitudesDenuncia(Long id) {
        denunciaRepository.findById(id).orElseThrow(() -> new RuntimeException("La denuncia con el ID " + id + " no existe"));
        return SolicitudResponse.from(solicitudRepository.findByDenunciaIdOrderByCreatedDateDesc(id));
    }

    /**
     * Crea y persiste una nueva solicitud en el sistema en base a los datos proporcionados.
     * La solicitud puede incluir un revisor asignado si es requerida y si el autor posee
     * el rol adecuado (por ejemplo, ADMIN).
     *
     * @param request Objeto {@link SolicitudRequest} que contiene la información necesaria para crear la solicitud,
     *                incluyendo título, mensaje, ID de la denuncia, ID del revisor opcional y el tipo de solicitud.
     */
    public void realizarSolicitud(SolicitudRequest request) {
        var autor = userRepository.findByUsernameAndEnabledTrue(JwtUtil.getCurrentUsername())
                .orElseThrow(() -> new RuntimeException("No se pudo obtener el usuario autenticado"));
        var denuncia = denunciaRepository.findById(request.idDenuncia())
                .orElseThrow(() -> new RuntimeException("No se pudo obtener la denuncia con ID " + request.idDenuncia()));
        Usuario revisor = null;
        if (autor.getRol().equals(Rol.ADMIN) && request.idRevisor() != null) {
            revisor = userRepository.findById(request.idRevisor())
                    .orElseThrow(() -> new RuntimeException("No se pudo obtener el usuario con ID " + request.idRevisor()));
        }
        Solicitud solicitud = Solicitud.builder()
                .titulo(request.titulo())
                .msg(request.msg())
                .denuncia(denuncia)
                .autor(autor)
                .revisor(revisor)
                .tipoSolicitud(request.tipoSolicitud())
                .build();
        solicitud = solicitudRepository.save(solicitud);

        if (autor.getRol().equals(Rol.ADMIN) && request.idRevisor() != null) {
            notificationService.notificar(
                    revisor,
                    solicitud.getTipoSolicitud().getTitulo(),
                    solicitud.getTipoSolicitud().getDescription(),
                    denuncia,
                    solicitud
            );
        } else if (autor.getRol().equals(Rol.MOD)) {
            var admins = userRepository.findAllByRol(Rol.ADMIN);
            Solicitud finalSolicitud = solicitud;
            admins.forEach(admin -> notificationService.notificar(
                    admin,
                    finalSolicitud.getTipoSolicitud().getTitulo(),
                    finalSolicitud.getTipoSolicitud().getDescription(),
                    denuncia,
                    finalSolicitud
            ));
        }
    }

    /**
     * Rechaza y actualiza el estado de una solicitud en función de los datos proporcionados.
     * Este método también asigna el usuario revisor y guarda los cambios en la base de datos.
     *
     * @param request objeto {@link SolicitudRevisionRequest} que contiene el ID de la solicitud,
     *                el estado de aprobación/rechazo, y un mensaje opcional de respuesta.
     * @return una instancia de {@link SolicitudResponse} que representa el estado actualizado
     *         de la solicitud tras haber sido procesada.
     * @throws RuntimeException si no se encuentra el usuario autenticado o la solicitud con el ID proporcionado.
     */
    @Transactional
    public SolicitudResponse revisarSolicitud(SolicitudRevisionRequest request) {
        var revisor = userRepository.findByUsernameAndEnabledTrue(JwtUtil.getCurrentUsername())
                .orElseThrow(() -> new RuntimeException("No se pudo obtener el usuario autenticado"));
        var solicitud = solicitudRepository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("No se pudo obtener la solicitud con ID " + request.id()));
        solicitud.setEstado(request.aprobado() ? Estado.RESUELTO : Estado.RECHAZADO);
        solicitud.setMsg(request.mensajeRespuesta());
        solicitud.setRevisor(revisor);
        solicitud.setEndDate(LocalDateTime.now());
        notificationService.notificar(
                solicitud.getAutor(),
                "Su solicitud ha sido " + (request.aprobado() ? "APROBADA." : "RECHAZADA."),
                solicitud.getTipoSolicitud().getDescription(),
                solicitud.getDenuncia(),
                solicitud
        );
        notificationService.marcarNotificacionesRelacionadasComoVistas(solicitud);
        return SolicitudResponse.from(solicitudRepository.save(solicitud));
    }

}
