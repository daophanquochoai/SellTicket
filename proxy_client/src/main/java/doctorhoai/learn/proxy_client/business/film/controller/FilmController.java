package doctorhoai.learn.proxy_client.business.film.controller;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.film.model.request.FilmRequest;
import doctorhoai.learn.proxy_client.business.film.service.FilmFeign;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/film-service/api/film")
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
    @GetMapping("/get/custom")
    public ResponseEntity<Response> getFilmByCustom(
            @RequestParam(defaultValue = "0", required = false) String page,
            @RequestParam(defaultValue = "10", required = false) String limit,
            @RequestParam(defaultValue = "", required = false) String q,
            @RequestParam(defaultValue = "asc", required = false) String asc,
            @RequestParam(defaultValue = "none", required = false) String status,
            @RequestParam(defaultValue = "name", required = false) String orderBy
    ){
        return filmFeign.getFilmByCustom(page, limit, q, asc, status, orderBy);
    }
    @GetMapping("/get/{branchId}/{time}")
    public ResponseEntity<Response> getFilmByBranchIdAndTime(
            @PathVariable @Valid @NotBlank String branchId,
            @PathVariable @Valid @NotNull LocalDate time
    ){
        return filmFeign.getFilmByBranchIdAndTime(branchId, time);
    }
    @GetMapping("/get/status/{status}")
    public ResponseEntity<Response> getFilmByStatus(
            @PathVariable @Valid @NotBlank String status
    ){
        return filmFeign.getFilmByStatus(status);
    }
    @GetMapping("/get/sub/{subId}")
    public ResponseEntity<Response> getFilmNotInSub(
            @PathVariable String subId
    ){
        return filmFeign.getFilmNotInSub(subId);
    }
}
