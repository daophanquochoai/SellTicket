package doctorhoai.learn.showtimeservice.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilmShowDto {
    private int id;
    private LocalDateTime timeEnd;
    private LocalDateTime timeStart;
    private FilmDto filmDto;
    private String roomId;
    private LocalDateTime timestamp;
    private boolean status;
}
