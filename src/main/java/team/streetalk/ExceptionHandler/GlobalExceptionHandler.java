package team.streetalk.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = ArithmeticException.class)
    public ResponseEntity<ErrorResponse> handleArithmeticException(ArithmeticException e){
        ErrorResponse response = new ErrorResponse();
        response.setStatus(e.getStatus());
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
