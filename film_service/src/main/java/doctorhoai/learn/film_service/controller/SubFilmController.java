package doctorhoai.learn.film_service.controller;

import doctorhoai.learn.film_service.dto.request.SubFilmRequest;
import doctorhoai.learn.film_service.dto.response.Response;
import doctorhoai.learn.film_service.service.inter.SubFilmService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get/sub/{subId}")
    public ResponseEntity<Response> getSubFilmBySubId(
            @PathVariable @Valid @NotBlank String subId,
            @RequestParam(defaultValue = "0", required = false) String page,
            @RequestParam(defaultValue = "10", required = false) String limit,
            @RequestParam(defaultValue = "asc", required = false) String asc
    ){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(200)
                        .message("Get Sub Film By Sub Id")
                        .data(subFilmService.getSubFilmBySubId(page,limit,asc,subId))
                        .build()
        );
    }

    @PostMapping("/add")
    public ResponseEntity<Response> addSubFilm(
            @RequestBody @Valid SubFilmRequest subFilmRequest
            ){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Response.builder()
                        .statusCode(201)
                        .message("Add Sub Film")
                        .data(subFilmService.addSubFilm(subFilmRequest))
                        .build()
        );
    }

    @DeleteMapping("/delete/{filmId}/{subId}")
    public ResponseEntity<Response> deleteById(
            @PathVariable @Valid @NotBlank String filmId,
            @PathVariable @Valid @NotBlank String subId
    ){
        boolean check = subFilmService.deleteSubFilm(filmId, subId);
        if( check ){
            return ResponseEntity.status(HttpStatus.OK).body(
                    Response.builder()
                            .statusCode(200)
                            .message("Delete Sub Film Successfully")
                            .build()
            );
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(
                    Response.builder()
                            .statusCode(400)
                            .message("Delete Sub Film Failed")
                            .build()
            );
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<Response> getAll(){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(200)
                        .message("Get subfilm successfully")
                        .data(subFilmService.getAll())
                        .build()
        );
    }
}
