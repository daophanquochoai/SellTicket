package doctorhoai.learn.showtimeservice.dto.request;


import java.sql.Time;
import java.time.LocalDateTime;

public class FilmShowRequest {
    private int id;
    private Time timeEnd;
    private Time timeStart;
    private LocalDateTime timestamp;
    private String status;
    private String filmId;
    private String roomId;
}
