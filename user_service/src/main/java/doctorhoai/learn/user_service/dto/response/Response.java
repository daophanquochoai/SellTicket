package doctorhoai.learn.user_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Response {
    private int statusCode;
    private String message;
    private Object data;
}
