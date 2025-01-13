package doctorhoai.learn.user_service.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
public class ErrorResponse {
    private int statusCode;
    private String message;
}
