package doctorhoai.learn.proxy_client.business.auth.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AuthenticationRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    @NotBlank(message = "*Username must not be empty!*")
    private String username;
    @NotNull(message = "*Password must not be null!*")
    @Min(value = 6, message = "*Passwod should have 6 character*")
    private String password;
}
