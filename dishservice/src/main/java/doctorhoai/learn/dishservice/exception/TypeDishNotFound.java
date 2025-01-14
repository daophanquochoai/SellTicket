package doctorhoai.learn.dishservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TypeDishNotFound extends RuntimeException{
    public TypeDishNotFound(String message) {
        super(message);
    }
}
