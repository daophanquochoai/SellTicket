package doctorhoai.learn.film_service.controller;

import doctorhoai.learn.film_service.dto.request.FilmRequest;
import doctorhoai.learn.film_service.dto.response.Response;
import doctorhoai.learn.film_service.entity.Film;
import doctorhoai.learn.film_service.service.inter.FilmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/film")
@Tag(name = "Film Controller", description = "Handler film operation")
public class FilmController {

    private final FilmService filmService;

    @Operation(
            summary = "Get all film in database"
    )
    @GetMapping("/all")
    public ResponseEntity<Response> getAllFilms() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Get all film successfully")
                                .data(filmService.getFilms())
                                .build()
                );
    }

    @Operation(
            summary = "Get film by id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Response> getFilmById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Get film successfully")
                                .data(filmService.getFilm(id))
                                .build()
                );
    }

    @Operation(
            summary = "Add film into database"
    )
    @PostMapping("/add")
    public ResponseEntity<Response> addFilm(@RequestBody @Valid FilmRequest film ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Add film successfully")
                                .data(filmService.addFilm(film))
                                .build()
                );
    }

    @Operation(
            summary = "Update file into database"
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateFilm(
            @PathVariable @NotBlank String id,
            @RequestBody @Valid FilmRequest film) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Update film successfully")
                                .data(filmService.updateFilm(id, film))
                                .build()
                );
    }

    @Operation(
            summary = "Delete (hidden) film"
    )
    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteFilm(
            @PathVariable @NotBlank String id
    ){
        filmService.deleteFilm(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Delete (hidden) film successfully")
                                .build()
                );
    }

    @Operation(
            summary = "Active film"
    )
    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activeFilm(
            @PathVariable @NotBlank String id
    ){
        filmService.activeFilm(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Active film successfully")
                                .build()
                );
    }
}
