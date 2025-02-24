package doctorhoai.learn.basedomain.Event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillChairTicket implements Serializable {
    private static final long serialVersionUID = 1L;
    private String chairCode;
    private Float price;
    private String conditionUse;
    private String name;
    private String typeTicket;
}
