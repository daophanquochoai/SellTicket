package doctorhoai.learn.proxy_client.business.user.model.request;

import doctorhoai.learn.proxy_client.business.user.model.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeRequest {
    @NotBlank(message = "Name not blank")
    private String name;
    @Length(min = 10,max = 12, message = "CCCD have length 10 - 12 characters")
    private String CCCD;
    @Email(message = "Email isn't in correct format")
    private String email;
    @NotBlank(message = "User name not blank")
    private String userName;
    @NotBlank(message = "Password not blank")
    @Length(min = 4, message = "Password has more than 4 characters")
    private String password;
    @NotNull(message = "Role isn't in format")
    private int roleId;
    private Status status;
}
