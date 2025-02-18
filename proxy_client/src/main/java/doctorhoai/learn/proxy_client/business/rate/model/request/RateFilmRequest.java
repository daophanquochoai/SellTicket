package doctorhoai.learn.proxy_client.business.rate.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RateFilmRequest {
    @Min( value = 1, message = "Star more than 1 star")
    @Max( value = 5, message = "Star more than 5 star")
    private int star;
    @NotBlank(message = "Content can't blank")
    private String content;
    @NotBlank(message = "Customer not found")
    private String customerId;
    @NotBlank(message = "Film not found")
    private String filmId;
    private String active;
}
