package doctorhoai.learn.showtimeservice.service.client.feign;

import doctorhoai.learn.showtimeservice.dto.response.Response;
import doctorhoai.learn.showtimeservice.service.client.fallback.FilmFallBack;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "filmservice", contextId = "filmServiceClient", fallbackFactory = FilmFallBack.class, path = "/film")
public interface FilmFeign {
    @GetMapping("/all")
    public ResponseEntity<Response> getAllFilms();

    @GetMapping("/{id}")
    public ResponseEntity<Response> getFilmById(@PathVariable String id);

    // chua can den
//    @PostMapping("/add")
//    public ResponseEntity<Response> addFilm(@RequestBody @Valid FilmRequest film );
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<Response> updateFilm(@PathVariable @NotBlank String id, @RequestBody @Valid FilmRequest film);

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteFilm(@PathVariable @NotBlank String id);

    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activeFilm(@PathVariable @NotBlank String id);
}
