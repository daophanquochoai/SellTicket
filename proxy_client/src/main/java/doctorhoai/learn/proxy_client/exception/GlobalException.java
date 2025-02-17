package doctorhoai.learn.proxy_client.exception;

import doctorhoai.learn.proxy_client.exception.payload.ExceptionMsg;
import feign.FeignException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.ExecutionException;

@ControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class
    })
    public <T extends BindException> ResponseEntity<ExceptionMsg> handleValidationException(final T e) {

        log.info("**ApiExceptionHandler controller, handle validation exception*\n");
        final var badRequest = HttpStatus.BAD_REQUEST;

        ExceptionMsg exceptionMsg =  ExceptionMsg.builder()
                .msg(e.getMessage())
                .httpStatus(badRequest)
                .timestamp(ZonedDateTime
                        .now(ZoneId.systemDefault()))
                .build();
        return ResponseEntity.status(badRequest).body(exceptionMsg);
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
    @ExceptionHandler({
            UnAuthorizedException.class,
            BadRequestException.class,
    })
    public <T extends RuntimeException>ResponseEntity<ExceptionMsg> handleApiRequestException( final T e){
        log.info("**ApiExceptionHandler controller, handle Api request**");
        final var badRequest = HttpStatus.BAD_REQUEST;
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
