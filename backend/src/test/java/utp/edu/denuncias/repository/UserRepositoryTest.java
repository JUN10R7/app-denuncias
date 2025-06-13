package utp.edu.denuncias.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import utp.edu.denuncias.enums.Rol;
import utp.edu.denuncias.model.Usuario;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp () {
        userRepository.deleteAll();
    }

    @Test
    public void debeGuardarUsuarioCorrectamente() {
        Usuario usuarioEsperado = crearUsuarioParaPrueba("test", "test", "test");

        Usuario usuarioGuardado = userRepository.save(usuarioEsperado);

        assertNotNull(usuarioGuardado.getId(), "El ID no debería ser nulo después de guardar");
        assertEquals(usuarioEsperado.getUsername(), usuarioGuardado.getUsername());
        assertEquals(usuarioEsperado.getDni(), usuarioGuardado.getDni());
        assertEquals(usuarioEsperado.getCorreo(), usuarioGuardado.getCorreo());
    }

    @Test
    public void debeEliminarUsuarioPorDni() {
        String dniPrueba = "12457812";
        Usuario usuario = crearUsuarioParaPrueba("noneno", dniPrueba, "<EMAIL>");
        userRepository.save(usuario);

        userRepository.deleteByDni(dniPrueba);
        Optional<Usuario> usuarioEliminado = userRepository.findByDni(dniPrueba);
        assertTrue(usuarioEliminado.isEmpty(), "El usuario debería haber sido eliminado");
    }

    @Test
    public void debeListarTodosLosUsuarios() {
        Usuario usuario1 = crearUsuarioParaPrueba("qwe", "qwe", "qwe");
        Usuario usuario2 = crearUsuarioParaPrueba("asd", "asd", "asd");
        userRepository.save(usuario1);
        userRepository.save(usuario2);

        List<Usuario> usuarios = userRepository.findAll();

        assertFalse(usuarios.isEmpty(), "La lista de usuarios no debería estar vacía");
        assertEquals(2, usuarios.size(),  "Deberían existir al menos 2 usuarios");
        assertTrue(usuarios.stream().anyMatch(u -> u.getDni().equals("qwe")));
        assertTrue(usuarios.stream().anyMatch(u -> u.getDni().equals("asd")));
    }

    private Usuario crearUsuarioParaPrueba(String name, String dni, String correo) {
        return Usuario.builder()
                .username(name)
                .password("Test123*")
                .nombres("Test")
                .apellidos("User")
                .dni(dni)
                .correo(correo)
                .rol(Rol.USER)
                .build();
    }
}