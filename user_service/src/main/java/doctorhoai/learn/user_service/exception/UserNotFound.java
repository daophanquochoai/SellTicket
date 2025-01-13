package doctorhoai.learn.user_service.exception;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFound extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public UserNotFound(String message) {
        super(message);
    }
}
