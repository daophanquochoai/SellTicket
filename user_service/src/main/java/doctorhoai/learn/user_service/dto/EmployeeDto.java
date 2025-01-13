package doctorhoai.learn.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDto {
    private String id;
    @NotBlank(message = "Name not blank")
    private String name;
    @Length(min = 10,max = 12, message = "CCCD have length 10 - 12 characters")
    private String CCCD;
    @Email(message = "Email isn't in correct format")
    private String email;
    private AccountDto account;
}
