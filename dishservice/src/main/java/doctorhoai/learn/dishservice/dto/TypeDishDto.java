package doctorhoai.learn.dishservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import doctorhoai.learn.dishservice.entity.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TypeDishDto {
    private String id;
    private String active;
    @NotBlank(message = "Name can't blank")
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<DishDto> dishes;
}
