package doctorhoai.learn.basedomain.Event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDishTicket implements Serializable {
    private static final long serialVersionUID = 1L;
    private Float price;
    private Integer amount;
    private String active;
    private String name;
    private String image;
    private String typeDish;
}
