package doctorhoai.learn.roomservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ChairDto {
    private String id;
    @NotBlank(message = "Name can't blank")
    private String name;
    private String description;
    @NotBlank(message = "Status can't blank")
    private String status;
}
