package doctorhoai.learn.paymentservice.dto;

import doctorhoai.learn.paymentservice.entity.Active;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillDishDto {
    private String id;
    private Active active;
    @Min(value = 1000)
    private Float price;
    @Min(value = 1)
    private Integer amount;
    private DishDto dishDto;
}
