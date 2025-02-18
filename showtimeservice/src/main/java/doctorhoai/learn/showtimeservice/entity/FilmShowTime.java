package doctorhoai.learn.showtimeservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "film_show_time")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilmShowTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "time_end")
    @NotNull
    private Time timeEnd;
    @Column(name = "time_start")
    @NotNull
    private Time timeStart;
    @NotNull
    private LocalDate timestamp;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "film_id")
    private String filmId;
    @Column(name = "room_id")
    @NotNull
    private String roomId;
}
