package doctorhoai.learn.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDto {
    private int id;
    @NotBlank(message = "Role name not blank")
    private String roleName;
    private String status;
}
