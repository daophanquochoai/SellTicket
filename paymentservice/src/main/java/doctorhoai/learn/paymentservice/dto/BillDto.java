package doctorhoai.learn.paymentservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import doctorhoai.learn.paymentservice.entity.Active;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @NotNull
    @Min(value = 1000)
    private Float totalPrice;
    private String transactionCode;
    @NotBlank
    private String paymentMethodId;
    private String paymentMethod;
    private Active active;
    @NotNull
    private List<BillChairDto> chairs;
    private List<BillDishDto> dishes;
    private LocalDateTime timestamp;
    private String status;
    @NotNull
    private Integer filmShowTimeId;
    private Time timeEnd;
    private Time timeStart;
    private LocalDate timeStampSee;
    @NotBlank
    private String roomId;
    private String nameRoom;
    @NotBlank
    private String filmId;
    private String nameFilm;
}
