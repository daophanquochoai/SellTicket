package doctorhoai.learn.paymentservice.service.sse;

import doctorhoai.learn.paymentservice.dto.BillChairDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private List<BillChairDto> chairs;
    private Integer filmShowTimeId;
    private String roomId;
    private String filmId;
}
