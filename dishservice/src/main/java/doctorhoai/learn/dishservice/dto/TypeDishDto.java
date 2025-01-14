package doctorhoai.learn.dishservice.dto;

import doctorhoai.learn.dishservice.entity.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TypeDishDto {
    private String id;
    private String active;
    @NotBlank(message = "Name can't blank")
    private String name;
}
