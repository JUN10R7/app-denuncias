package utp.edu.denuncias.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import utp.edu.denuncias.enums.Rol;
import utp.edu.denuncias.model.Token;
import utp.edu.denuncias.model.Usuario;
import utp.edu.denuncias.security.JwtUtil;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TokenRepositoryTest {

    @BeforeEach
    @AfterEach
    void setUp () {
        tokenRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtUtil jwtUtil;

    @Test
    public void generarToken() {
        userRepository.save(
                Usuario.builder()
                        .username("test")
                        .password("Test123*")
                        .nombres("Test")
                        .apellidos("User")
                        .dni("12345455")
                        .correo("correo")
                        .rol(Rol.USER)
                        .build()
        );
        Usuario userGuardado = userRepository.findByDni("12345455").orElse(new Usuario());
        Token token = jwtUtil.generateToken(userGuardado);

        Token tokenGuardado = tokenRepository.save(token);
        assertEquals(token.getToken(), tokenGuardado.getToken(), "Los tokens deben ser iguales");
        assertEquals(token.getUsuario(), tokenGuardado.getUsuario(), "Los usuarios deben ser iguales");
    }

    @Test
    public void listarTokensPorUsuario() {
        generarToken();
        List<Token> tokens = tokenRepository.findAllByUsuarioAndExpiredFalseAndRevokedFalse(userRepository.findByDni("12345455").orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
        assertFalse(tokens.isEmpty(), "La lista de tokens no debería estar vacía");
        assertFalse(tokens.stream().anyMatch(Token::isExpired), "No deberia estar expirado");
        assertFalse(tokens.stream().anyMatch(Token::isRevoked), "No deberia estar revocado");
    }

    @Test
    public void revokeToken() {
        userRepository.save(
                Usuario.builder()
                        .username("test")
                        .password("Test123*")
                        .nombres("Test")
                        .apellidos("User")
                        .dni("12345455")
                        .correo("correo")
                        .rol(Rol.USER)
                        .build()
        );
        Usuario userGuardado = userRepository.findByDni("12345455").orElse(new Usuario());
        Token token = jwtUtil.generateToken(userGuardado);

        Token tokenGuardado = tokenRepository.save(token);

        int filasAfectadas = tokenRepository.revokeToken(tokenGuardado.getToken());

        tokenGuardado = tokenRepository.findByToken(tokenGuardado.getToken()).orElseThrow(() ->
                new RuntimeException("Token no encontrado"));

        assertEquals(1, filasAfectadas, "Se debe afectar al menos 1 fila");
        assertTrue(tokenGuardado.isRevoked(), "El token deberia estar revocado");

    }

}