package doctorhoai.learn.paymentservice.dto;

import doctorhoai.learn.paymentservice.entity.Active;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillDto {
    private String id;
    @NotNull(message = "Total price can't not null")
    @Min(value = 1000, message = "Total price have more than 1,000đ")
    private Float totalPrice;
    private String transactionCode;
    @NotBlank(message = "Payment method id can't blank")
    @NotNull(message = "Payment method id can't null")
    private String paymentMethodId;
    private String paymentMethod;
    private Active active;
    @NotNull(message = "Chairs have more than 0")
    private List<BillChairDto> chairs;
    private List<BillDishDto> dishes;
    private LocalDateTime timestamp;
    private String status;
    @NotNull(message = "Film show time id can't null")
    private Integer filmShowTimeId;
    private Time timeEnd;
    private Time timeStart;
    private LocalDate timeStampSee;
    @NotBlank(message = "Room id can't blank")
    @NotNull(message = "Room id can't null")
    private String roomId;
    private String nameRoom;
    @NotBlank(message = "Film id can't not blank")
    @NotNull(message = "Film id can't not null")
    private String filmId;
    private String nameFilm;
    @NotNull(message = "Username can't null")
    @NotBlank(message = "Username can't blank")
    private String userName;
    @NotBlank(message = "Email can't blank")
    @NotNull(message = "Email can't null")
    @Email(message = "Email isn't format")
    private String email;
    @NotNull(message = "Number phone can't null")
    @NotBlank(message = "Number phone can't blank")
    @Length(min = 10, max = 12, message = "Number Phone isn't format")
    private String numberPhone;
}
