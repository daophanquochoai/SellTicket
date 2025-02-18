package doctorhoai.learn.showtimeservice.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class ErrorResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private int statusCode;
    private String message;
    public ErrorResponse(){}
    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
