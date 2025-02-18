package doctorhoai.learn.showtimeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
