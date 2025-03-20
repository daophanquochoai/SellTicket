package doctorhoai.learn.film_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FilmShowDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private Time timeEnd;
    private Time timeStart;
    private String subFilmId;
    private String roomId;
    private LocalDate timestamp;
    private String status;
}
