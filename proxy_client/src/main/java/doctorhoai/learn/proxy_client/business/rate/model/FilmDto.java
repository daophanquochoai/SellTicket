package doctorhoai.learn.proxy_client.business.rate.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilmDto {
    private String id;
    private String name;
    private int age;
    private String sub;
    private String description;
    private String content;
    private String trailer;
    private List<TypeFilmDto> typeFilms;
    private String status;
}