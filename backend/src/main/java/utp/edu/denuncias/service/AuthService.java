package utp.edu.denuncias.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import utp.edu.denuncias.dto.UsuarioRequest;
import utp.edu.denuncias.dto.AuthRequest;
import utp.edu.denuncias.model.Usuario;
import utp.edu.denuncias.repository.UserRepository;
import utp.edu.denuncias.security.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
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
    public void register(UsuarioRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }

        Usuario u = Usuario.builder()
                .nombres(request.nombres())
                .apellidos(request.apellidos())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .dni(request.dni())
                .email(request.email())
                .rol(request.rol())
                .build();
        userRepository.save(u);
    }

    /**
     * Autentica a un usuario en el sistema utilizando sus credenciales y genera un token JWT
     * en caso de autenticación exitosa.
     *
     * @param request un objeto {@link AuthRequest} que contiene el nombre de usuario
     *                y la contraseña para la autenticación.
     * @return un token JWT generado para el usuario*/
    public String login(AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        Usuario usuario = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));


        return jwtUtil.generateToken(usuario);
    }

}