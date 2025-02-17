package doctorhoai.learn.proxy_client.business.auth.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthenticationResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String accessToken;
}
