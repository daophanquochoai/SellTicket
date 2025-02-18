package doctorhoai.learn.proxy_client.business.film.service;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.film.model.request.FilmRequest;
import doctorhoai.learn.proxy_client.business.film.service.fallback.FilmFeignFallBack;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "filmservice", contextId = "filmServiceClient", path = "/film", fallbackFactory = FilmFeignFallBack.class)
public interface FilmFeign {

    @GetMapping("/all")
    public ResponseEntity<Response> getAllFilms();

    @GetMapping("/{id}")
    public ResponseEntity<Response> getFilmById(@PathVariable String id);

    @PostMapping("/add")
    public ResponseEntity<Response> addFilm(@RequestBody @Valid FilmRequest film );

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateFilm(@PathVariable @NotBlank String id, @RequestBody @Valid FilmRequest film);

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteFilm(@PathVariable @NotBlank String id);

    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activeFilm(@PathVariable @NotBlank String id);
}
