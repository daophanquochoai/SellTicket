package doctorhoai.learn.roomservice.dto.resquest;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoomRequest {
    @NotBlank(message = "Name room can't blank")
    private String name;
    @NotNull( message = "Position Chair isn't null")
    private Integer[][] positionChair;
    @NotBlank( message = "Branch isn't blank")
    private String branchId;
    @NotBlank( message = "Status isn't bank")
    private String status;
    private Integer slot;
}
