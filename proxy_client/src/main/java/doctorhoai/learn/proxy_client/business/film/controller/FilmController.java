package doctorhoai.learn.proxy_client.business.film.controller;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.film.model.request.FilmRequest;
import doctorhoai.learn.proxy_client.business.film.service.FilmFeign;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/film")
public class FilmController {

    private final FilmFeign filmFeign;

    @GetMapping("/all")
    public ResponseEntity<Response> getAllFilms(){
        return filmFeign.getAllFilms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getFilmById(@PathVariable String id){
        return filmFeign.getFilmById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Response> addFilm(@RequestBody @Valid FilmRequest film ){
        return filmFeign.addFilm(film);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateFilm(@PathVariable @NotBlank String id, @RequestBody @Valid FilmRequest film){
        return filmFeign.updateFilm(id, film);
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteFilm(@PathVariable @NotBlank String id){
        return filmFeign.deleteFilm(id);
    }

    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activeFilm(@PathVariable @NotBlank String id){
        return filmFeign.activeFilm(id);
    }
}
