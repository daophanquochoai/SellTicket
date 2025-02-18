package doctorhoai.learn.showtimeservice.exception;

import doctorhoai.learn.showtimeservice.dto.response.ErrorResponse;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
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

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.info("** Process method argument not valid **");
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> errors = new ArrayList<>();
        for(FieldError fieldError : fieldErrors){
            errors.add(fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse( HttpStatus.BAD_REQUEST.value(),String.join("\n",errors))
        );
    }

    @ExceptionHandler(
            value = {
                    ErrorException.class,
            }
    )
    public ResponseEntity<ErrorResponse> handleErrorException( Exception ex, WebRequest request){
        log.info("** Process error exception **");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage() )
        );
    }
    @ExceptionHandler(value = {
            FeignException.class,
            FeignException.FeignServerException.class,
            FeignException.FeignClientException.class,
    })
    public <T extends FeignException> ResponseEntity<ErrorResponse> handleProxyException(final T e){
        log.info("** Process feign exception **");
        final var badRequest = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(HttpStatus.valueOf(503))
                .body(
                        new ErrorResponse(503, e.getMessage() )
                );
    }


}
