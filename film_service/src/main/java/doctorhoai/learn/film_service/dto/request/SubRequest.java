package doctorhoai.learn.film_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubRequest {
    private String id;
    @NotBlank(message = "Tên không thể trống")
    private String name;
}
