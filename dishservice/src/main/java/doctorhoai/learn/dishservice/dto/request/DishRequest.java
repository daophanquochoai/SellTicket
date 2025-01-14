package doctorhoai.learn.dishservice.dto.request;

import doctorhoai.learn.dishservice.entity.Status;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DishRequest {
    private String id;
    @Min(value = 1000, message = "Price has more than 1000")
    private float price;
    private Status active;
    @NotBlank(message = "Dish name can't blank")
    private String name;
    private String image;
    @NotBlank(message = "Dish should belong to one group")
    private String typeDishId;
}
