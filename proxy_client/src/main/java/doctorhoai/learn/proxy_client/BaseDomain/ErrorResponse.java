package doctorhoai.learn.proxy_client.BaseDomain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
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
