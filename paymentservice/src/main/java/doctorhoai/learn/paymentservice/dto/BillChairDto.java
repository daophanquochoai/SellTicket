package doctorhoai.learn.paymentservice.dto;

import doctorhoai.learn.paymentservice.entity.Active;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillChairDto {
    private String id;
    @NotBlank
    private String chairCode;
    @Min(value = 1000)
    private Float price;
    @NotNull
    private TicketDto ticket;
    private Active active;
}
