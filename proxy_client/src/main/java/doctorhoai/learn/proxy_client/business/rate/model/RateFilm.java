package doctorhoai.learn.proxy_client.business.rate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
