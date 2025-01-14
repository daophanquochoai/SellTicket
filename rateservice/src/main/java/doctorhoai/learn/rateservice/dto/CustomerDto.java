package doctorhoai.learn.rateservice.dto;

import doctorhoai.learn.rateservice.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {
    private String id;
    private String name;
    private String phoneNumber;
    private String email;
    private String userName;
    private String password;
    private int roleId;
    private Status status;
}
