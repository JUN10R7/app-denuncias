package utp.edu.denuncias.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import utp.edu.denuncias.model.Usuario;
import utp.edu.denuncias.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Carga los detalles de un usuario basándose en su nombre de usuario.
     * Recupera el usuario del repositorio y construye un objeto {@link UserDetails}
     * con el nombre de usuario, la contraseña y el rol del usuario.
     *
     * @param username el nombre de usuario del usuario cuyos detalles se desean cargar
     * @return una instancia de {@link UserDetails} que contiene la información del usuario
     * @throws UsernameNotFoundException si no se encuentra un usuario con el nombre de usuario proporcionado en el repositorio
     */
    @Override
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException{
        Usuario user = userRepository.findByUsernameAndEnabledTrue(username)
                .orElseThrow(()-> new UsernameNotFoundException("No encontrado"));
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRol().name())
                .build();
    }


}