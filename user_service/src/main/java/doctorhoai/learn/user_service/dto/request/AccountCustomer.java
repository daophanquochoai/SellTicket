package doctorhoai.learn.user_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountCustomer implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotBlank(message = "Name not blank")
    private String name;
    @Length(min = 10, max = 11)
    private String phoneNumber;
    @Email(message = "Email isn't in correct format")
    private String email;
}
