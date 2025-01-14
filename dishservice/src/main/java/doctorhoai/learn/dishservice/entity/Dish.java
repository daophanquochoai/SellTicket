package doctorhoai.learn.dishservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private float price;
    @Enumerated(EnumType.STRING)
    private Status active;
    private String name;
    private String image;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_dish_id")
    private TypeDish typeDish;
}

