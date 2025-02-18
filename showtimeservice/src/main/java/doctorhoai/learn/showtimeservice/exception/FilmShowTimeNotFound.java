package doctorhoai.learn.showtimeservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FilmShowTimeNotFound extends RuntimeException {
    public FilmShowTimeNotFound(String message) {
        super(message);
    }
}
