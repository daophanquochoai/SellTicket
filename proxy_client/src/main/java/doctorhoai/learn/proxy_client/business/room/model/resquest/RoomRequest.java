package doctorhoai.learn.proxy_client.business.room.model.resquest;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoomRequest {
    private String name;
    private int[][] positionChair;
    private String branchId;
    private String status;
}
