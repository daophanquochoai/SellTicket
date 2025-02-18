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
    @NotBlank(message = "Name branch can't blank")
    private String nameBranch;
    @NotBlank(message = "Address can't blank")
    private String address;
    @NotBlank(message = "Active can't blank")
    private String status;
}

