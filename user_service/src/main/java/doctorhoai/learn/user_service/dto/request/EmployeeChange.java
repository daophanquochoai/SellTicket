package doctorhoai.learn.user_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeChange {
    @NotBlank(message = "Name not blank")
    private String name;
    @Length(min = 10,max = 12, message = "CCCD have length 10 - 12 characters")
    private String CCCD;
    @Email(message = "Email isn't in correct format")
    private String email;
    private String password;
}
