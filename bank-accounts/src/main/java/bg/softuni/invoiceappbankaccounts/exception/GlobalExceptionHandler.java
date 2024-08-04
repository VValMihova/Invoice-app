package bg.softuni.invoiceappbankaccounts.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(ObjectNotFoundException.class)
  public ResponseEntity<Void> handleObjectNotFoundException() {
    return ResponseEntity.notFound().build();
  }
  
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
    return ResponseEntity.badRequest().body(ex.getMessage());
  }
  
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    List<String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.toList());
    return ResponseEntity.badRequest().body(errors);
  }
  
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleAllExceptions(Exception ex) {
    ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
        ex.getLocalizedMessage(), List.of("An unexpected error occurred"));
    return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
  }
  
}