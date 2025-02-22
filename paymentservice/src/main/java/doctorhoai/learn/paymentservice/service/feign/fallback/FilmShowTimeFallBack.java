package doctorhoai.learn.paymentservice.service.feign.fallback;

import doctorhoai.learn.paymentservice.dto.response.Response;
import doctorhoai.learn.paymentservice.service.feign.FilmShowTimeFeign;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class FilmShowTimeFallBack implements FilmShowTimeFeign {
    @Override
    public ResponseEntity<Response> getFilmShowByDate(String roomId, LocalDate date) {
        return ResponseEntity
                .status(HttpStatus.valueOf(429))
                .body(
                        Response
                                .builder()
                                .statusCode(429)
                                .message("FilmShowTimeService not found")
                                .build()
                );
    }

    @Override
    public ResponseEntity<Response> getFilmShowByRoomAndId(String roomId, Integer id) {
        return ResponseEntity
                .status(HttpStatus.valueOf(429))
                .body(
                        Response
                                .builder()
                                .statusCode(429)
                                .message("FilmShowTimeService not found")
                                .build()
                );
    }

    @Override
    public ResponseEntity<Response> getFilmShowTime(Integer id) {
        return ResponseEntity
                .status(HttpStatus.valueOf(429))
                .body(
                        Response
                                .builder()
                                .statusCode(429)
                                .message("FilmShowTimeService not found")
                                .build()
                );
    }

}
