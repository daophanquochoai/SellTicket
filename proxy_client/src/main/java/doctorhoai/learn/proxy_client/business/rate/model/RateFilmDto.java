package doctorhoai.learn.proxy_client.business.rate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RateFilmDto {
    private String id;
    private int star;
    private String content;
    private LocalDateTime timeStamp;
    private CustomerDto customer;
    private FilmDto film;
    private Status active;
}
