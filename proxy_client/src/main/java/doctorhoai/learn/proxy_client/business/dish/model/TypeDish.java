package doctorhoai.learn.proxy_client.business.dish.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TypeDish {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Enumerated(EnumType.STRING)
    private Status active;
    private String name;
    @OneToMany(mappedBy = "typeDish", fetch = FetchType.LAZY)
    private List<Dish> dish;
}
