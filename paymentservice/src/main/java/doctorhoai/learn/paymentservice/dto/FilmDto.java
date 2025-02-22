package doctorhoai.learn.paymentservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FilmDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    @NotBlank(message = "Name can't blank")
    private String name;
    @Min(value = 0, message = "Age has value more than 0")
    @Max(value = 100, message = "Age has value less than 100")
    private int age;
    private String sub;
    private String description;
    private String content;
    private String trailer;
    private List<TypeFilmDto> typeFilms;
    @Pattern(regexp = "^(ACTIVE|DELETE)$", message = "Active should value 'ACTIVE' or 'DELETE'")
    private String status;
}
