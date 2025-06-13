package utp.edu.denuncias.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import utp.edu.denuncias.dto.AuthRegisterRequest;
import utp.edu.denuncias.dto.AuthRequest;
import utp.edu.denuncias.model.Token;
import utp.edu.denuncias.model.Usuario;
import utp.edu.denuncias.repository.TokenRepository;
import utp.edu.denuncias.repository.UserRepository;
import utp.edu.denuncias.security.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Registra un nuevo usuario en el sistema utilizando la información proporcionada en la solicitud.
     *
     * @param request un objeto {@link AuthRequest} que contiene los datos del usuario,
     *                como el nombre de usuario y la contraseña, necesarios para el registro.
     * @throws IllegalArgumentException si el nombre de usuario ya está en uso.
     */
    public void register(AuthRegisterRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }

        Usuario u = Usuario.builder()
                .nombres(request.nombres())
                .apellidos(request.apellidos())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .dni(request.dni())
                .correo(request.correo())
                .rol(request.rol())
                .build();
        userRepository.save(u);
    }

    /**
     * Maneja el proceso de inicio de sesión de un usuario autentificando sus credenciales,
     * generando un token JWT, y almacenándolo en la base de datos.
     *
     * @param request un objeto {@link AuthRequest} que contiene el nombre de usuario y la contraseña
     *                del usuario que desea autenticarse.
     * @return una cadena de texto que representa el token JWT generado tras la autenticación exitosa.
     * @throws UsernameNotFoundException si el usuario
     *         no se encuentra en el sistema.
     * @throws org.springframework.security.authentication.BadCredentialsException si las credenciales
     *         proporcionadas no son válidas.
     */
    public String login(AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        Usuario usuario = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        Token token = jwtUtil.generateToken(usuario);
        tokenRepository.save(token);

        return token.getToken();
    }

}