package doctorhoai.learn.user_service.dto.response;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

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
