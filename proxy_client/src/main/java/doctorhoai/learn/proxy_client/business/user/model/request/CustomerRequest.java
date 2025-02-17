package doctorhoai.learn.proxy_client.business.user.model.request;

import doctorhoai.learn.proxy_client.business.user.model.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequest {
    @NotBlank(message = "Name not blank")
    private String name;
    @Length(min = 10, max = 11)
    private String phoneNumber;
    @Email(message = "Email isn't in correct format")
    private String email;
    @NotBlank(message = "User name not blank")
    private String userName;
    @Length(min = 4, message = "Password has more than 4 characters")
    private String password;
    @NotNull(message = "Role isn't in format")
    private int roleId;
    private Status status;
}
