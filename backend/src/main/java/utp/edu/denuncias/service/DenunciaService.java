package utp.edu.denuncias.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import utp.edu.denuncias.dto.DenunciaCambiarEstadoRequest;
import utp.edu.denuncias.dto.DenunciaRequest;
import utp.edu.denuncias.dto.DenunciaResponse;
import utp.edu.denuncias.enums.Estado;
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
        Usuario user = userRepository.findByUsername(username)
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
        Usuario user = userRepository.findByUsername(JwtUtil.getCurrentUsername())
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
            return DenunciaResponse.from(denunciaRepository.findAllByModAsignadoIsNullAndEstadoIsNot(Estado.ARCHIVADO));
        }
        var user = userRepository.findByUsername(JwtUtil.getCurrentUsername())
                .orElseThrow(() -> new UsernameNotFoundException("No se pudo obtener el usuario autenticado"));
        return DenunciaResponse.from(denunciaRepository.findAllByModAsignadoIdAndEstadoIsNot(user.getId(), Estado.ARCHIVADO));
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
        var user = userRepository.findByUsername(JwtUtil.getCurrentUsername())
                .orElseThrow(() -> new UsernameNotFoundException("No se pudo obtener el usuario autenticado"));
        var denuncia = denunciaRepository.findByUsuarioIdAndId(user.getId(), id)
                .orElseThrow(() -> new RuntimeException("La denuncia con el ID " + id + " no existe"));
        if (denuncia.getEstado() != Estado.PENDIENTE) {
            throw new AccessDeniedException("Solo se pueden editar denuncias en estado PENDIENTE");
        }
        denuncia.setTitulo(request.titulo());
        denuncia.setDescription(request.description());
        denuncia.setLugar(request.lugar());
        denuncia.setCategoria(request.categoria());
        return DenunciaResponse.from(denunciaRepository.save(denuncia));
    }

    /**
     * Cambia el estado de una denuncia existente en el sistema basado en la solicitud proporcionada.
     *
     * @param request el objeto {@code DenunciaCambiarEstadoRequest} que contiene el ID de la denuncia
     *                y el nuevo estado al que se desea actualizar.
     * @return un objeto {@code DenunciaResponse} que representa la denuncia actualizada
     *         con su nuevo estado.
     * @throws RuntimeException si no se encuentra una denuncia con el ID proporcionado en la solicitud.
     */
    @Transactional
    public DenunciaResponse cambiarEstadoDenuncia(DenunciaCambiarEstadoRequest request) {
        var denuncia = denunciaRepository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("La denuncia con el ID " + request.id() + " no existe"));
        denuncia.setEstado(request.estado());
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
        return DenunciaResponse.from(denunciaRepository.save(denuncia));
    }

    /**
     * Elimina una denuncia del sistema si cumple con los criterios establecidos.
     * La denuncia debe existir y debe tener el estado PENDIENTE para poder ser eliminada.
     *
     * @param id el identificador único de la denuncia que se desea eliminar
     *           y que debe estar registrada en el sistema.
     * @throws RuntimeException si la denuncia con el ID proporcionado no existe en el sistema.
     * @throws AccessDeniedException si la denuncia no se encuentra en estado PENDIENTE, ya que
     *                                solo es posible eliminar denuncias en ese estado.
     */
    public void eliminarDenuncia(Long id) {
        var denuncia = denunciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La denuncia con el ID " + id + " no existe"));
        if (denuncia.getEstado() != Estado.PENDIENTE) {
            throw new AccessDeniedException("Solo se pueden eliminar denuncias con estado PENDIENTE");
        }
        denunciaRepository.deleteDenunciaByIdAndEstado(id, Estado.PENDIENTE);
    }
}