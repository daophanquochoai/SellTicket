package doctorhoai.learn.film_service.controller;

import doctorhoai.learn.film_service.dto.response.Response;
import doctorhoai.learn.film_service.service.inter.SubFilmService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sub/film")
public class SubFilmController {

    private final SubFilmService subFilmService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getSubFilmById(
            @PathVariable @Valid @NotBlank String id
    ){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(200)
                        .message("Get Sub Film By Id")
                        .data(subFilmService.getSubFilmById(id))
                        .build()
        );
    }
    @GetMapping("/get/{filmId}/{subId}")
    public ResponseEntity<Response> getSubFilmByFilmIdAndSubId(
            @PathVariable @Valid @NotBlank String filmId,
            @PathVariable @Valid @NotBlank String subId
    ){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(200)
                        .message("Get Sub Film By Film Id and Sub Id")
                        .data(subFilmService.getSubFilmByFilmIdAndSubId(filmId, subId))
                        .build()
        );
    }
}
