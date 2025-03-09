package doctorhoai.learn.showtimeservice.dto.response.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoomDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private Integer[][] positionChair;
    private BranchDto branch;
    private String status;
}
