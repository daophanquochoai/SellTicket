package doctorhoai.learn.proxy_client.business.time.model;

import doctorhoai.learn.proxy_client.business.film.model.FilmDto;
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
    private FilmDto filmDto;
    private String roomId;
    private LocalDate timestamp;
    private String status;
}
