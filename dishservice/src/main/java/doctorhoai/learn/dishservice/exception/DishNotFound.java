package doctorhoai.learn.dishservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DishNotFound extends RuntimeException {
    public DishNotFound(String message) {
        super(message);
    }
}
