package doctorhoai.learn.basedomain.Event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketEmail implements Serializable {
    private static final long serialVersionUID = 1L;
    private Float totalPrice;
    private String transactionCode;
    private String paymentMethodId;
    private String paymentMethod;
    private List<BillChairTicket> chairs;
    private List<BillDishTicket> dishes;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Time timeEnd;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Time timeStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate timeStampSee;
    private String nameRoom;
    private String nameFilm;
    private String userName;
    private String email;
    private String numberPhone;
}
