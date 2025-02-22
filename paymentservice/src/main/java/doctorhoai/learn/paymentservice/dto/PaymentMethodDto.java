package doctorhoai.learn.paymentservice.dto;

import doctorhoai.learn.paymentservice.entity.Active;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentMethodDto {
    private String id;
    @NotBlank(message = "Method can't blank")
    private String method;
    private Active active;
}
