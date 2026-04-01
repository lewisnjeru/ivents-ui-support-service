package ivents.ivents_ui_support.exception;

import ivents.ivents_ui_support.dto.response.EntityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import java.util.*;

@Slf4j
@ControllerAdvice
public class CustomControllerAdvisor {
    private static String getString(Exception ex) {
        Throwable cause = ex;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }

        // Check if the root cause is a DecodingException or JsonParseException
        String errorMessage;
        String[] errorMessageArray;
        if (cause instanceof org.springframework.core.codec.DecodingException) {
            errorMessage = "Decoding error: " + cause.getMessage();
        } else if (cause instanceof com.fasterxml.jackson.core.JsonParseException) {
            errorMessage = "JSON parsing error: " + cause.getMessage();
        } else {
            errorMessage = ex.getLocalizedMessage();
        }
        return errorMessage;
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<EntityResponse> handleHttpRequestMethodNotSupported(MethodNotAllowedException ex, ServerHttpRequest request) {

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(EntityResponse.builder()
                        .status(false)
                        .code(HttpStatus.METHOD_NOT_ALLOWED.value())
                        .message("Specified HTTP method not allowed.")
                        .errors(Collections.singletonList(ex.getMessage()))
                        .build());
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<EntityResponse> handleMethodArgumentNotValid(WebExchangeBindException ex, ServerHttpRequest request) {
        List<String> errorsList = ex.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return (ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(EntityResponse.builder()
                        .status(false)
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Request validation failed.")
                        .errors(errorsList)
                        .build()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

                return (ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(EntityResponse.builder()
                        .status(false)
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Request validation failed.")
                        .errors(new ArrayList<>(errors.values()))
                        .build()));
    }

    @ExceptionHandler(UnsupportedMediaTypeStatusException.class)
    public ResponseEntity<EntityResponse> handleHttpMediaTypeNotSupported(UnsupportedMediaTypeStatusException ex, ServerHttpRequest request) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(EntityResponse.builder()
                        .status(false)
                        .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                        .message("Request validation failed.")
                        .errors(Collections.singletonList(ex.getLocalizedMessage()))
                        .build());
    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<EntityResponse> handleValidationErrors(CustomValidationException ex) {
        log.error("CustomValidationException: {}", ex.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(EntityResponse.builder()
                        .status(false)
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(ex.getLocalizedMessage())
                        .errors(Collections.singletonList(ex.getLocalizedMessage()))
                        .build());
    }

   @ExceptionHandler(Exception.class)
    public ResponseEntity<EntityResponse> handleGenericErrors(Exception ex) {
        String errorMessage = getString(ex);

       log.error("Exception: {}", ex.getLocalizedMessage());
        log.error("Exception:", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(EntityResponse.builder()
                        .status(false)
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Request not properly formatted.")
                        .errors(Collections.singletonList(errorMessage))
                        .build());
    }

}
