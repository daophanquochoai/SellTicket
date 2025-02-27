package doctorhoai.learn.proxy_client.business.user.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    @NotBlank(message = "User name not blank")
    private String userName;
    @NotBlank(message = "Password not blank")
    @Min(value = 4, message = "Password has more than 4 characters")
    private String password;
    private RoleDto role;
    private String active;
}
