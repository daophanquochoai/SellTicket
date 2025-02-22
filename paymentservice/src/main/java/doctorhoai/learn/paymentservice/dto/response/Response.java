package doctorhoai.learn.paymentservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
public class Response implements Serializable {
    private static final long serialVersionUID = 1L;
    private int statusCode;
    private String message;
    private Object data;
}
