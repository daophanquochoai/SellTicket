package doctorhoai.learn.proxy_client.business.payment.model;

import doctorhoai.learn.proxy_client.business.dish.model.DishDto;
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
