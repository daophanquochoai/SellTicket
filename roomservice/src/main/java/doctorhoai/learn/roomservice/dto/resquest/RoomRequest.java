package doctorhoai.learn.roomservice.dto.resquest;


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
    private String name;
    private Integer[][] positionChair;
    private String branchId;
    private String status;
}
