package doctorhoai.learn.proxy_client.business.dish.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DishDto {
    private String id;
    private float price;
    private String active;
    private String name;
    private String image;
    private TypeDishDto typeDish;
}
