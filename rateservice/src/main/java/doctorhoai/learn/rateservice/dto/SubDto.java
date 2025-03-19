package doctorhoai.learn.rateservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<FilmShowDto> filmShowDtos;
    public SubDto(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
