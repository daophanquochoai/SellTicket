package doctorhoai.learn.proxy_client.business.room.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchDto  {
    private String id;
    @NotBlank(message = "Tên địa chỉ không thể trống")
    private String nameBranch;
    @NotBlank(message = "Địa chỉ không thể trống")
    private String address;
    @NotBlank(message = "Trạng thái không thể trống")
    private String status;
}

