package pe.edu.lab16.evento.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ApiError> build(HttpStatus s, String msg, Map<String, String> det) {
        return ResponseEntity.status(s).body(new ApiError(LocalDateTime.now(), s.value(), s.getReasonPhrase(), msg, det));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> notFound(ResourceNotFoundException e) { return build(HttpStatus.NOT_FOUND, e.getMessage(), null); }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> invalid(MethodArgumentNotValidException e) {
        Map<String, String> errores = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(f -> errores.put(f.getField(), f.getDefaultMessage()));
        return build(HttpStatus.BAD_REQUEST, "Datos de entrada inválidos", errores);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApiError> malformed(Exception e) {
        return build(HttpStatus.BAD_REQUEST, "JSON mal formado o valor con formato incorrecto (fecha yyyy-MM-dd, estado válido)", null);
    }

    @ExceptionHandler({BusinessException.class, DataIntegrityViolationException.class})
    public ResponseEntity<ApiError> conflict(Exception e) {
        String m = e instanceof BusinessException ? e.getMessage() : "Violación de integridad (dato duplicado o relacionado)";
        return build(HttpStatus.CONFLICT, m, null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> generic(Exception e) { return build(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", null); }
}
