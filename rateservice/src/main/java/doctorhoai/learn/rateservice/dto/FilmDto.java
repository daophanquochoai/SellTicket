package doctorhoai.learn.rateservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilmDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private int age;
    private String image;
    private String nation;
    private String duration;
    private List<SubDto> sub;
    private String description;
    private String content;
    private String trailer;
    private List<TypeFilmDto> typeFilms;
    private String status;
}