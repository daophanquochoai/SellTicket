package doctorhoai.learn.paymentservice.exception;

import doctorhoai.learn.paymentservice.dto.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> errors = new ArrayList<>();
        for(FieldError fieldError : fieldErrors){
            errors.add(fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse( HttpStatus.BAD_REQUEST.value(), String.join("\n",errors))
        );
    }

    @ExceptionHandler( value = {
            ErrorException.class,
            Exception.class,
    })
    public ResponseEntity<ErrorResponse> handleGlobalException( Exception ex, WebRequest webRequest){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse( HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage())
        );
    }
    @ExceptionHandler( value = {
            TicketNotFound.class,
            ShowTimNotFound.class,
            PaymentNotFound.class,
            DishNotFound.class,
            RoomNotFound.class,
            BillNotFound.class,
            FilmNotFound.class
    })
    public ResponseEntity<ErrorResponse> handleObjectNotFound( Exception ex, WebRequest webRequest){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponse( HttpStatus.NOT_FOUND.value(), ex.getMessage())
        );
    }

}
