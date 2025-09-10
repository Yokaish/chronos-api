package br.com.chronos.infrastructure.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationException(MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors();
        var body = Map.of(
                "status", 400,  // aqui você coloca o número do erro HTTP
                "error", "Bad Request",
                "messages", errors.stream().map(ErrorValidationDataDTO::new).toList()
        );

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgumentException(IllegalArgumentException ex) {
        var error = new ErrorDTO(400, ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(java.time.format.DateTimeParseException.class)
    public ResponseEntity handleDateTimeParseException(java.time.format.DateTimeParseException ex) {
        var error = new ErrorDTO(400, ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }


    private record ErrorDTO(int status, String message) {}


    private record ErrorValidationDataDTO(String code, String field, String message) {
        public ErrorValidationDataDTO(FieldError error) {
            this(error.getCode(), error.getField(), error.getDefaultMessage());
        }
    }

}
