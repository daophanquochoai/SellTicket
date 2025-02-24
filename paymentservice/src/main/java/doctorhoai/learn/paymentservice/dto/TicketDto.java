package doctorhoai.learn.paymentservice.dto;

import doctorhoai.learn.paymentservice.entity.Active;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank(message = "Condition Use can't blank")
    @NotNull(message = "Condition Use can't null")
    private String conditionUse;
    @NotBlank(message = "Name can't blank")
    @NotNull(message = "Name can't null")
    private String name;
    @Min(value = 0, message = "Price have more than 0đ")
    private String price;
    @NotBlank(message = "Type Ticket can't blank")
    private String typeTicket;
}
