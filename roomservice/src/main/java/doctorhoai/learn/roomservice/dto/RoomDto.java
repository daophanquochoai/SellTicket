package doctorhoai.learn.roomservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoomDto {
    private String id;
    private String name;
    private int[][] positionChair;
    private BranchDto branch;
    private String status;
}
