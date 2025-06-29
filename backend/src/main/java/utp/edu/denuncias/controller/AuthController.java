package utp.edu.denuncias.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import utp.edu.denuncias.dto.UsuarioRequest;
import utp.edu.denuncias.dto.AuthRequest;
import utp.edu.denuncias.dto.AuthResponse;
import utp.edu.denuncias.service.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Registra un nuevo usuario en el sistema utilizando los datos proporcionados en la solicitud.
     *
     * @param auth un objeto {@link AuthRequest} que contiene el nombre de usuario y la contraseña del usuario
     * @return una cadena de texto confirmando que el usuario ha sido registrado correctamente
     */
    @PostMapping("/registro")
    public String registrar(@RequestBody UsuarioRequest auth){
        authService.register(auth);
        return "Usuario registrado correctamente";
    }

    /**
     * Maneja el proceso de inicio de sesión del usuario validando las credenciales y generando un token JWT.
     * Este método autentica al usuario utilizando el nombre de usuario y la contraseña proporcionados,
     * recupera al usuario de la base de datos, genera un token JWT firmado tras una autenticación exitosa,
     * almacena el token en la base de datos y lo devuelve en la respuesta.
     *
     * @param request la solicitud de autenticación que contiene el nombre de usuario y la contraseña del usuario
     * @return un {@link ResponseEntity} que contiene un {@link AuthResponse} con el token JWT generado
     *
     * @throws BadCredentialsException si las credenciales proporcionadas no son válidas
     * @throws UsernameNotFoundException si el usuario no existe en el sistema
     */
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request){
        String token = authService.login(request);
        return ResponseEntity.ok(new AuthResponse(token));
    }

}
