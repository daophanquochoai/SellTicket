package doctorhoai.learn.paymentservice.dto;

import doctorhoai.learn.paymentservice.entity.Active;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TicketDto {
    private String id;
    private Active active;
    @NotBlank
    private String conditionUse;
    @NotBlank
    private String name;
    @Min(value = 0)
    private String price;
    @NotBlank
    private String typeTicket;
}
