package utp.edu.denuncias.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import utp.edu.denuncias.repository.TokenRepository;

/**
 * Servicio encargado de gestionar operaciones relacionadas con los tokens.
 * Principalmente revoca tokens existentes cuando es necesario.
 */

@Service
@RequiredArgsConstructor
public class TokenService {

    /** Repositorio para acceder y gestionar los tokens almacenados en la base de datos */
    private final TokenRepository tokenRepository;

    /**
     * Revoca un token existente, marcándolo como invalidado.
     * Si el token se encuentra en el repositorio, su estado se actualiza a "revoked = true".
     *
     * @param token el valor del token que se desea revocar
     */
    public void revokeToken(String token) {
        tokenRepository.findByToken(token)
                .ifPresent(tokenEntity -> {
                    tokenEntity.setRevoked(true);
                    tokenRepository.save(tokenEntity);
                });
    }

}