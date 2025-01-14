package doctorhoai.learn.rateservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RateFilm {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private int star;
    private String content;
    private LocalDateTime timeStamp;
    private String customerId;
    private String filmId;
    private Status active;
}
