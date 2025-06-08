package doctorhoai.learn.proxy_client.exception;

import doctorhoai.learn.proxy_client.BaseDomain.ErrorResponse;
import doctorhoai.learn.proxy_client.exception.payload.ExceptionMsg;
import feign.FeignException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalException extends ResponseEntityExceptionHandler {

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
    @ExceptionHandler({
        FeignException.class,
            FeignException.FeignServerException.class,
            FeignException.FeignClientException.class,
            ExecutionException.class
    })
    public <T extends FeignException> ResponseEntity<ExceptionMsg> handleProxyException( final T e){
        log.info("** Api Exception Handler controller, handle feign proxy exception**");
        final var badRequest = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(
                ExceptionMsg.builder()
                        .msg(e.contentUTF8())
                        .httpStatus(badRequest)
                        .timestamp(ZonedDateTime
                                .now(ZoneId.systemDefault()))
                        .build(), badRequest);
    }
    @ExceptionHandler(exception = {
            UnAuthorizedException.class,
            BadRequestException.class,
            ExpiredJwtException.class,
            BadCredentialsException.class,
            MalformedJwtException.class
    })
    public <T extends RuntimeException>ResponseEntity<ExceptionMsg> handleApiRequestException( final T e){
        log.info("**ApiExceptionHandler controller, handle Api request**");
        final var badRequest = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(badRequest).body(
                ExceptionMsg.builder()
                        .msg(e.getMessage())
                        .httpStatus(badRequest)
                        .timestamp(ZonedDateTime.now(ZoneId.systemDefault()))
                        .build()
        );
    }
    @ExceptionHandler({
            NotFoundException.class
    })
    public <T extends RuntimeException>ResponseEntity<ExceptionMsg> handleNotFoundException( final T e){
        log.info("**ApiExceptionHandler controller, handle Api request**");
        final var internal = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(internal).body(
                ExceptionMsg.builder()
                        .msg(e.getMessage())
                        .httpStatus(internal)
                        .timestamp(ZonedDateTime.now(ZoneId.systemDefault()))
                        .build()
        );
    }
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleGlobalException(Exception exception,
                                                        WebRequest webRequest) {
        log.info("**ApiExceptionHandler controller, handle exception**");
        final var internal = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(internal).body(
                ExceptionMsg.builder()
                        .msg(exception.getMessage())
                        .httpStatus(internal)
                        .timestamp(ZonedDateTime.now(ZoneId.systemDefault()))
                        .build()
        );
    }
}
