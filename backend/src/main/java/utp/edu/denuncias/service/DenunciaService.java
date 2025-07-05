package utp.edu.denuncias.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import utp.edu.denuncias.dto.DenunciaRequest;
import utp.edu.denuncias.dto.DenunciaResponse;
import utp.edu.denuncias.enums.Estado;
import utp.edu.denuncias.enums.Rol;
import utp.edu.denuncias.model.Denuncia;
import utp.edu.denuncias.model.Usuario;
import utp.edu.denuncias.repository.DenunciaRepository;
import utp.edu.denuncias.repository.UserRepository;
import utp.edu.denuncias.security.JwtUtil;

import org.springframework.security.access.AccessDeniedException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DenunciaService {

    private final DenunciaRepository denunciaRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    /**
     * Crea una nueva denuncia en el sistema basada en los datos proporcionados en la solicitud.
     *
     * @param request el objeto {@code DenunciaRequest} que contiene los detalles de la denuncia a registrar,
     *                incluyendo título, descripción, lugar y categoría.
     * @return una instancia de {@code DenunciaResponse} que representa la denuncia registrada
     *         en el sistema, junto con los datos estructurados resultantes del proceso.
     */
    public DenunciaResponse nuevaDenuncia(DenunciaRequest request) {
        String username = JwtUtil.getCurrentUsername();
        Usuario user = userRepository.findByUsernameAndEnabledTrue(username)
                .orElseThrow(() -> new UsernameNotFoundException("No se pudo obtener el usuario autenticado"));
        Denuncia denuncia = Denuncia.builder()
                .titulo(request.titulo())
                .description(request.description())
                .lugar(request.lugar())
                .categoria(request.categoria())
                .usuario(user)
                .build();
        return DenunciaResponse.from(denunciaRepository.save(denuncia));
    }

    /**
     * Recupera una lista de denuncias asociadas a un usuario específico, identificado por su ID,
     * y las convierte en una lista de objetos {@link DenunciaResponse}.
     */
    public List<DenunciaResponse> listarAllDenunciasUsuario(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se pudo obtener el usuario con ID " + id));

        return DenunciaResponse.from(denunciaRepository.findByUsuarioId(id));
    }

    public List<DenunciaResponse> listarAllDenunciasMod(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se pudo obtener el usuario con ID " + id));

        return DenunciaResponse.from(denunciaRepository.findAllByModAsignadoId(user.getId()));
    }

    /**
     * Recupera una lista de denuncias asociadas al usuario actualmente autenticado en el sistema.
     *
     * @return una lista de objetos {@code DenunciaResponse} que representan las denuncias registradas
     *         por el usuario autenticado, extraídas de la base de datos.
     * @throws UsernameNotFoundException si no es posible obtener los datos del usuario autenticado.
     */
    public List<DenunciaResponse> listarDenunciasUsuario() {
        Usuario user = userRepository.findByUsernameAndEnabledTrue(JwtUtil.getCurrentUsername())
                .orElseThrow(() -> new UsernameNotFoundException("No se pudo obtener el usuario autenticado"));
        return DenunciaResponse.from(denunciaRepository.findByUsuarioId(user.getId()));
    }

    /**
     * Recupera todas las denuncias disponibles en el sistema y las convierte en una lista
     * de objetos {@code DenunciaResponse}.
     *
     * @return una lista de objetos {@code DenunciaResponse} que representan todas las denuncias
     *         registradas en el sistema.
     */
    public List<DenunciaResponse> listarAllDenuncias() {
        return DenunciaResponse.from(denunciaRepository.findAll());
    }

    /**
     * Recupera una denuncia específica identificada por su ID.
     *
     * @param id el identificador único de la denuncia que se desea encontrar.
     * @return un objeto {@code DenunciaResponse} que representa la denuncia encontrada
     *         en el sistema.
     * @throws RuntimeException si no se encuentra una denuncia con el ID proporcionado.
     */
    public DenunciaResponse listarDenuncia(Long id) {
        return DenunciaResponse.from(denunciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La denuncia con el ID " + id + " no existe")));
    }

    /**
     * Recupera una lista de denuncias asignadas al moderador actualmente autenticado
     * que no tienen el estado ARCHIVADO.
     *
     * @return una lista de objetos {@code DenunciaResponse} que representan las denuncias
     *         asignadas al moderador autenticado y que no han sido archivadas.
     * @throws UsernameNotFoundException si no es posible obtener los datos del usuario autenticado.
     */
    public List<DenunciaResponse> listarDenunciasMod(Boolean all) {
        if (all) {
            return DenunciaResponse.from(denunciaRepository.findAllByModAsignadoIsNullAndEstadoNotIn(List.of(Estado.RESUELTO, Estado.RECHAZADO)));
        }
        var user = userRepository.findByUsernameAndEnabledTrue(JwtUtil.getCurrentUsername())
                .orElseThrow(() -> new UsernameNotFoundException("No se pudo obtener el usuario autenticado"));
        return DenunciaResponse.from(denunciaRepository.findAllByModAsignadoIdAndEstadoNotIn(user.getId(), List.of(Estado.RESUELTO, Estado.RECHAZADO)));
    }

    /**
     * Edita una denuncia existente asociada al usuario autenticado, siempre que
     * se encuentre en estado PENDIENTE. Actualiza los campos de la denuncia con
     * los valores proporcionados en la solicitud.
     *
     * @param id Identificador único de la denuncia a editar.
     * @param request Objeto que contiene los nuevos datos para la denuncia,
     * incluyendo título, descripción, lugar y categoría.
     * @return Una instancia de {@code DenunciaResponse} que representa los datos
     * actualizados de la denuncia.
     * @throws UsernameNotFoundException Si no se encuentra el usuario autenticado
     * asociado a la solicitud.
     * @throws RuntimeException Si no existe una denuncia asociada al usuario
     * autenticado con el ID proporcionado.
     * @throws AccessDeniedException Si la denuncia no se encuentra en estado
     * PENDIENTE y, por tanto, no puede ser editada.
     */
    @Transactional
    public DenunciaResponse editarDenuncia(Long id, DenunciaRequest request) {
        var user = userRepository.findByUsernameAndEnabledTrue(JwtUtil.getCurrentUsername())
                .orElseThrow(() -> new UsernameNotFoundException("No se pudo obtener el usuario autenticado"));
        var denuncia = denunciaRepository.findByUsuarioIdAndId(user.getId(), id)
                .orElseThrow(() -> new RuntimeException("La denuncia con el ID " + id + " no existe"));
        if (!denuncia.getEstado().equals(Estado.PENDIENTE)) {
            throw new AccessDeniedException("Solo se pueden editar denuncias en estado PENDIENTE");
        }
        denuncia.setTitulo(request.titulo());
        denuncia.setDescription(request.description());
        denuncia.setLugar(request.lugar());
        denuncia.setCategoria(request.categoria());
        return DenunciaResponse.from(denunciaRepository.save(denuncia));
    }

    /**
     * Actualiza el estado de una denuncia y notifica al usuario asociado sobre el cambio.
     *
     * @param id El identificador único de la denuncia que se desea actualizar.
     * @param estado El nuevo estado de la denuncia, representado como una cadena; debe coincidir con un valor en el enum Estado.
     * @return Una instancia de DenunciaResponse que contiene la información actualizada de la denuncia.
     * @throws RuntimeException Si no se encuentra una denuncia con el ID proporcionado.
     */
    @Transactional
    public DenunciaResponse cambiarEstadoDenuncia(Long id, String estado) {
        var denuncia = denunciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La denuncia con el ID " + id + " no existe"));
        denuncia.setEstado(Estado.valueOf(estado));
        notificationService.notificar(
                denuncia.getUsuario(),
                "Se ha actualizado el estado de su denuncia.",
                "El estado de su denuncia ha pasado a estado " + denuncia.getEstado().getTitulo() + ".",
                denuncia,
                null
        );
        return DenunciaResponse.from(denunciaRepository.save(denuncia));
    }

    /**
     * Asigna un moderador a una denuncia específica.
     *
     * @param idDenuncia el identificador único de la denuncia que se desea actualizar
     * @param idModerador el identificador único del moderador que se asignará a la denuncia
     * @return un objeto DenunciaResponse que representa la denuncia actualizada con el moderador asignado
     * @throws RuntimeException si la denuncia o el moderador no existen
     */
    @Transactional
    public DenunciaResponse asignarModerador(Long idDenuncia, Long idModerador) {
        var denuncia = denunciaRepository.findById(idDenuncia)
                .orElseThrow(() -> new RuntimeException("La denuncia con el ID " + idDenuncia + " no existe"));
        var moderador = userRepository.findById(idModerador)
                .orElseThrow(() -> new RuntimeException("El moderador con el ID " + idModerador + " no existe"));
        denuncia.setModAsignado(moderador);
        denuncia.setEstado(Estado.EN_PROCESO);
        return DenunciaResponse.from(denunciaRepository.save(denuncia));
    }

    /**
     * Elimina una denuncia cambiando su estado a ELIMINADO. Solo se permite eliminar denuncias con estado PENDIENTE.
     *
     * @param id el identificador único de la denuncia que se desea eliminar
     * @throws RuntimeException si no se encuentra una denuncia con el ID especificado
     * @throws AccessDeniedException si la denuncia no tiene el estado PENDIENTE
     */
    @Transactional
    public void eliminarDenuncia(Long id) {
        var denuncia = denunciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La denuncia con el ID " + id + " no existe"));
        if (!denuncia.getEstado().equals(Estado.PENDIENTE) || JwtUtil.getCurrentUserRole().equals(Rol.ADMIN.name())) {
            throw new AccessDeniedException("Solo se pueden eliminar denuncias con estado PENDIENTE");
        }
        denuncia.setEstado(Estado.ELIMINADO);
        denunciaRepository.save(denuncia);
    }
}