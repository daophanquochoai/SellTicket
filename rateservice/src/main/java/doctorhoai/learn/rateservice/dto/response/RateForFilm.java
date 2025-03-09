package doctorhoai.learn.rateservice.dto.response;

import doctorhoai.learn.rateservice.dto.RateFilmDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RateForFilm {
    private double rate;
    private List<RateFilmDto> comments;
}
