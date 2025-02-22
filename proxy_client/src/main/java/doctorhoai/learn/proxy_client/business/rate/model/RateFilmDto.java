package doctorhoai.learn.proxy_client.business.rate.model;

import doctorhoai.learn.proxy_client.business.film.model.FilmDto;
import doctorhoai.learn.proxy_client.business.user.model.request.CustomerRequest;
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
    private CustomerRequest customer;
    private FilmDto film;
    private Status active;
}
