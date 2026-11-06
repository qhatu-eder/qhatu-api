package dev.eder.qhatu_api.common.exception;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import dev.eder.qhatu_api.common.dto.ErrorRespuesta;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 400: validación de un record con @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorRespuesta> manejarValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> detalles = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> detalles.put(error.getField(), error.getDefaultMessage()));
        return armarRespuesta(HttpStatus.BAD_REQUEST, "Hay datos invalidos en la peticion ", detalles);
    }

    // 400: JSON roto o un enum que no existe
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorRespuesta> manejarJsonInvalido(HttpMessageNotReadableException ex) {
        return armarRespuesta(HttpStatus.BAD_REQUEST, "El JSON  no es valido ", Map.of());
    }

    // 400: falta un ?parametro= obligatorio
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorRespuesta> manejarParametroFaltante(MissingServletRequestParameterException ex) {
        return armarRespuesta(HttpStatus.BAD_REQUEST, "Falta un parametro obligatorio",
                Map.of(ex.getParameterName(), "Es obligatorio"));
    }

    // 400: /api/productos/abc cuando se espera un número
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorRespuesta> manejarTipoInvalido(MethodArgumentTypeMismatchException ex){
        return armarRespuesta(HttpStatus.BAD_REQUEST, "Un parametro tiene un valor no permitido", Map.of(ex.getName(), "Valor no valido: " + ex.getValue()));
    }

    // 400: Excel demasiado grande

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorRespuesta> manejarArchivoGrande(MaxUploadSizeExceededException ex){
        return armarRespuesta(HttpStatus.BAD_REQUEST, "El archivo pasa el limite de  5 MB", Map.of());
    }

    // 401: usuario o contraseña incorrectos
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorRespuesta> manejarCredenciales(BadCredentialsException ex){
        return armarRespuesta(HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectas", Map.of());
    }

    // 401: usuario desactivado por el admin
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorRespuesta> manejarDesactivado(DisabledException ex){
        return armarRespuesta(HttpStatus.UNAUTHORIZED, "Tu usuario esta desactivado. Habla con el administrador ", Map.of());
    }

    // 402: licencia vencida o sin activar
    @ExceptionHandler(LicenciaInvalidaException.class)
    public ResponseEntity<ErrorRespuesta> manejarLicencia(LicenciaInvalidaException ex){
        return armarRespuesta(HttpStatus.PAYMENT_REQUIRED, ex.getMessage(), Map.of());
    }

    // 403: tiene sesión, pero su rol no puede hacer esto (@PreAuthorize)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorRespuesta> manejarSinPermiso(AccessDeniedException ex){
        return armarRespuesta(HttpStatus.FORBIDDEN, "Tu rol no tiene permiso para esta accion", Map.of());
    }


    // 404: el recurso no existe
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorRespuesta> manejarNoEncontrado(RecursoNoEncontradoException ex){
        return armarRespuesta(HttpStatus.NOT_FOUND, ex.getMessage(), Map.of());
    }
    
    // 404: la ruta no existe
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorRespuesta> manejarRutaInexistente(NoResourceFoundException ex){
        return  armarRespuesta(HttpStatus.NOT_FOUND, "La ruta " + ex.getResourcePath() + " no existe", Map.of());
    }

    // 409: regla de negocio
    @ExceptionHandler(ReglaNegocioException.class)
    public ResponseEntity<ErrorRespuesta> manejarReglaNegocio(ReglaNegocioException ex){
        return armarRespuesta(HttpStatus.CONFLICT, ex.getMessage(), Map.of());
    }

    // 409: la base rechazó un duplicado (UNIQUE)
    @ExceptionHandler(DataIntegrityViolationException.class) 
    public ResponseEntity<ErrorRespuesta> manejarDuplicado(DataIntegrityViolationException ex){
        return armarRespuesta(HttpStatus.CONFLICT, "El registro choca con otro que ya existe", Map.of());
    }

    // 500: cualquier otro error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorRespuesta> manejarGeneral(Exception ex){
        log.error("Ocurrio un error inesperado", ex);
        return armarRespuesta(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error en el servidor", Map.of());
    }

    private ResponseEntity<ErrorRespuesta> armarRespuesta(HttpStatus estado, String mensaje,
            Map<String, String> detalles) {
        return ResponseEntity.status(estado)
                .body(new ErrorRespuesta(estado.value(), mensaje, detalles, LocalDateTime.now()));
    }
}
