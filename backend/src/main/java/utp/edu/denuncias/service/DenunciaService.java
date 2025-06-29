package utp.edu.denuncias.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import utp.edu.denuncias.dto.DenunciaRequest;
import utp.edu.denuncias.dto.DenunciaResponse;
import utp.edu.denuncias.model.Denuncia;
import utp.edu.denuncias.repository.DenunciaRepository;
import utp.edu.denuncias.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DenunciaService {

    private final DenunciaRepository denunciaRepository;
    private final UserRepository userRepository;


    /**
     * Crea y guarda una nueva denuncia en el sistema utilizando la información proporcionada en la solicitud.
     *
     * @param request un objeto {@link DenunciaRequest} que contiene los datos necesarios para crear una denuncia.
     *                Incluye detalles como título, descripción, lugar, categoría, y el nombre de usuario relacionado.
     * @return un objeto {@link Denuncia} que representa la denuncia creada y persistida en la base de datos.
     * @throws UsernameNotFoundException si el nombre de usuario proporcionado no se encuentra en el sistema.
     */
    public DenunciaResponse nuevaDenuncia(DenunciaRequest request) {

        var user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + request.username()));

        Denuncia d = Denuncia.builder()
                .titulo(request.titulo())
                .description(request.description())
                .lugar(request.lugar())
                .categoria(request.categoria())
                .usuario(user)
                .build();
        return buildDenunciaResponse(denunciaRepository.save(d));
    }

    /**
     * Elimina una denuncia del sistema basada en su identificador único.
     *
     * @param id el identificador único de la denuncia que se desea eliminar.
     */
    public void eliminarDenuncia(Long id) {
        denunciaRepository.deleteById(id);
    }

    /**
     * Recupera una lista de denuncias asociadas a un usuario específico, identificado por su ID,
     * y las convierte en una lista de objetos {@link DenunciaResponse}.
     */
    public List<DenunciaResponse> listarDenunciasUsuario(Long id) {
        List<DenunciaResponse> responses = new ArrayList<>();
        denunciaRepository.findByUsuarioId(id).forEach(denuncia -> responses.add(
                buildDenunciaResponse(denuncia)
        ));
        return responses;
    }

    /**
     * Recupera una lista de todas las denuncias existentes en el sistema, convirtiéndolas
     * en objetos de tipo {@link DenunciaResponse}.
     *
     * @return una lista de instancias {@link DenunciaResponse} que representan todas las denuncias
     *         almacenadas en la base de datos.
     */
    public List<DenunciaResponse> listarDenuncias() {
        List<DenunciaResponse> responses = new ArrayList<>();
        denunciaRepository.findAll().forEach(denuncia -> responses.add(
                buildDenunciaResponse(denuncia)
        ));
        return responses;

    }

    /**
     * Builds a {@link DenunciaResponse} object based on the provided {@link Denuncia} entity.
     *
     * @param denuncia the {@link Denuncia} entity containing the necessary information
     *                 to populate the response, including title, description, location,
     *                 category, status, username, and creation date.
     * @return a {@link DenunciaResponse} object containing the structured response data
     *         derived from the given {@link Denuncia} entity.
     */
    private DenunciaResponse buildDenunciaResponse(Denuncia denuncia) {
        return DenunciaResponse.builder()
                .titulo(denuncia.getTitulo())
                .description(denuncia.getDescription())
                .lugar(denuncia.getLugar())
                .categoria(denuncia.getCategoria().name())
                .estado(denuncia.getEstado().toString())
                .username(denuncia.getUsuario().getUsername())
                .fecha(denuncia.getCreatedDate().toString())
                .build();
    }
}