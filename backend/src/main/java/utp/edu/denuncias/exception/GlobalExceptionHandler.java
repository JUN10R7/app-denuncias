package utp.edu.denuncias.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utp.edu.denuncias.dto.ErrorResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones de tipo {@code UsernameNotFoundException} y retorna una respuesta de error
     * estructurada con un estado HTTP 404 (Not Found).
     *
     * @param ex la excepción {@code UsernameNotFoundException} lanzada cuando no se encuentra un usuario con el nombre especificado
     * @return un {@code ResponseEntity} que contiene un {@code ErrorResponse} con los detalles del error
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFound(UsernameNotFoundException ex) {
        return new ResponseEntity<>(
                new ErrorResponse("Usuario no encontrado", ex.getMessage(), HttpStatus.NOT_FOUND.value()),
                HttpStatus.NOT_FOUND
        );
    }

    /**
     * Maneja excepciones de tipo {@code BadCredentialsException} y retorna una respuesta de error
     * estructurada con un estado HTTP 401 (Unauthorized).
     *
     * @param ex la excepción {@code BadCredentialsException} lanzada cuando las credenciales proporcionadas no son válidas
     * @return un {@code ResponseEntity} que contiene un {@code ErrorResponse} con los detalles del error
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        return new ResponseEntity<>(
                new ErrorResponse("Credenciales inválidas", ex.getMessage(), HttpStatus.UNAUTHORIZED.value()),
                HttpStatus.UNAUTHORIZED
        );
    }

    /**
     * Maneja excepciones genéricas y retorna una respuesta de error estructurada con un estado HTTP 500 (Internal Server Error).
     *
     * @param ex la excepción {@code Exception} que ocurrió y debe ser manejada
     * @return un {@code ResponseEntity} que contiene un {@code ErrorResponse} con los detalles del error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        return new ResponseEntity<>(
                new ErrorResponse("Error inesperado", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    /**
     * Maneja excepciones de tipo {@code IllegalArgumentException} y retorna una respuesta de error
     * estructurada con un estado HTTP 400 (Bad Request).
     *
     * @param ex la excepción {@code IllegalArgumentException} lanzada cuando surge un argumento no válido
     * @return un {@code ResponseEntity} que contiene un {@code ErrorResponse} con los detalles del error
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return new ResponseEntity<>(
                new ErrorResponse("ERROR", ex.getMessage(), HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST
        );
    }
}
