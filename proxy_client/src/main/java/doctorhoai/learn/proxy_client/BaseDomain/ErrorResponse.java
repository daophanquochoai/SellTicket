package doctorhoai.learn.proxy_client.BaseDomain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorResponse {
    private int statusCode;
    private String message;
}
