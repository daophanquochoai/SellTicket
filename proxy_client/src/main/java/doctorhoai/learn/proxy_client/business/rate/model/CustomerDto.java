package doctorhoai.learn.proxy_client.business.rate.model;

import doctorhoai.learn.rateservice.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
