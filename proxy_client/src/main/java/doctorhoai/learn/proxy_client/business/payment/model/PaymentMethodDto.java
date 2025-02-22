package doctorhoai.learn.proxy_client.business.payment.model;

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
