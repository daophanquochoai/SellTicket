package doctorhoai.learn.roomservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RoomNotFound extends RuntimeException {
    public RoomNotFound(String message) {
        super(message);
    }
}
