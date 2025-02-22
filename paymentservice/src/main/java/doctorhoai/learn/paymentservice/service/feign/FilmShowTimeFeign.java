package doctorhoai.learn.paymentservice.service.feign;

import doctorhoai.learn.paymentservice.dto.response.Response;
import doctorhoai.learn.paymentservice.service.feign.fallback.FilmShowTimeFallBack;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@FeignClient(
        name = "showtimeservice",
        contextId = "showtimeServiceClient",
        path = "/filmshowtime",
        fallback = FilmShowTimeFallBack.class
)
public interface FilmShowTimeFeign {

    @GetMapping("/{roomId}/all")
    public ResponseEntity<Response> getFilmShowByDate(@PathVariable @NotNull String roomId, @RequestParam(required = true) LocalDate date);
    @GetMapping("/rom/{roomId}/showtime/{id}")
    public ResponseEntity<Response> getFilmShowByRoomAndId(@PathVariable @NotNull String roomId, @PathVariable @NotNull Integer id);
    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getFilmShowTime(@Valid @PathVariable @NotNull Integer id);
}
