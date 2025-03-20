package doctorhoai.learn.showtimeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DishDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private float price;
    private String active;
    private String name;
    private String image;
    private TypeDishDto typeDish;
}
