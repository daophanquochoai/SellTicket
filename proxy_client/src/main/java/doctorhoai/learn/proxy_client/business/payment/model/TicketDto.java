package doctorhoai.learn.proxy_client.business.payment.model;

import jakarta.validation.constraints.Max;
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
    @NotBlank(message = "Condition use isn't blank")
    private String conditionUse;
    @NotBlank( message = "Name isn't blank")
    private String name;
    @Min(value = 0, message = "Price should be large than 0")
    private Integer price;
    @NotBlank( message = "Type ticket isn't blank")
    private String typeTicket;
    @NotNull(message = "Slot isn't null")
    @Min( value = 0, message = "Slot should be large than 0")
    @Max(value = 10, message = "Slot should be less than 10")
    private Integer slot;
}
