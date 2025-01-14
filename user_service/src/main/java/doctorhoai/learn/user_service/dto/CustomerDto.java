package doctorhoai.learn.user_service.dto;

import doctorhoai.learn.user_service.entity.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {
    private String id;
    @NotBlank(message = "Name not blank")
    private String name;
    @Length(min = 10, max = 11)
    private String phoneNumber;
    @NotBlank(message = "Email not blank")
    @Email(message = "Email isn't in correct format")
    private String email;
    private AccountDto account;
    private LocalDate timestamp;
    private String status;
}
