package utp.edu.denuncias.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import utp.edu.denuncias.model.Denuncia;
import utp.edu.denuncias.model.Usuario;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DenunciaRepositoryTest {

    @BeforeEach
    @AfterEach
    void setUp () {
        denunciaRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Autowired
    DenunciaRepository denunciaRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    void debeCrearDenunciaCorrectamente() {
        Denuncia denuncia = denunciaRepository.save(
                Denuncia.builder()
                        .titulo("Test")
                        .descripcion("Test")
                        .lugar("Test")
                        .categoria("Test")
                        .usuario(nuevoUser())
                        .build()
        );

        List<Denuncia> denuncias = denunciaRepository.findAll();
        assertEquals(1,denuncias.size(), "Debe haber una denuncia en la lista");
        assertTrue(denuncias.stream().anyMatch(d -> d.getId().equals(denuncia.getId())), "Las denuncias deben contener el ID de la denuncia creada");
    }

    Usuario nuevoUser(){
        return userRepository.save(Usuario.builder()
                .nombres("Test")
                .apellidos("User")
                .username("test")
                .password("<PASSWORD>")
                .dni("12345455")
                .correo("<EMAIL>")
                .build());
    }

}