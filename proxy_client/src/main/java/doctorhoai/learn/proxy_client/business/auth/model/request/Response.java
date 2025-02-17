package doctorhoai.learn.proxy_client.business.auth.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Response {
    private HttpStatus status;
    private String message;
}
