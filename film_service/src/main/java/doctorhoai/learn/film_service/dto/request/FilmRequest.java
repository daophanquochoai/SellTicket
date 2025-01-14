package doctorhoai.learn.film_service.dto.request;

import doctorhoai.learn.film_service.entity.TypeFilm;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public class FilmRequest {
    @NotBlank(message = "Name can't blank")
    private String name;
    @Min(value = 0, message = "Age has value more than 0")
    @Max(value = 100, message = "Age has value less than 100")
    private int age;
    private String sub;
    private String description;
    private String content;
    private String trailer;
    private List<TypeFilm> typeFilms;
    @Pattern(regexp = "^(ACTIVE|DELETE)$", message = "Active should value 'ACTIVE' or 'DELETE'")
    private String status;
    @NotBlank(message = "Film should ")
    private String typeFilmId;
}
