package doctorhoai.learn.paymentservice.dto;

import doctorhoai.learn.paymentservice.entity.Active;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishDto {
    private String id;
    private float price;
    private String active;
    private String name;
    private String image;
    private TypeDishDto typeDish;
}
