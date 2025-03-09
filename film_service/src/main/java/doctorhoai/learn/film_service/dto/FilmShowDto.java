package doctorhoai.learn.film_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilmShowDto {
    private int id;
    private Time timeEnd;
    private Time timeStart;
    private String subFilmId;
    private String roomId;
    private LocalDate timestamp;
    private String status;
}
