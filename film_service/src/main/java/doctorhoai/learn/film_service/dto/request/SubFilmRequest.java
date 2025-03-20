package doctorhoai.learn.film_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SubFilmRequest {
    @NotBlank(message = "Loại phim không được để trống")
    private String subId;
    @NotBlank(message = "Phim không được để trống")
    private String filmId;
}
