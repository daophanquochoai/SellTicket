package doctorhoai.learn.paymentservice.dto;

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
public class FilmShowDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private Time timeEnd;
    private Time timeStart;
    private FilmDto filmDto;
    private String roomId;
    private LocalDate timestamp;
    private String status;
}
