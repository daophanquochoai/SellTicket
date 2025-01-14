package doctorhoai.learn.film_service.controller;

import doctorhoai.learn.film_service.dto.TypeFilmDto;
import doctorhoai.learn.film_service.dto.response.Response;
import doctorhoai.learn.film_service.service.inter.TypeFilmService;
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
@RequestMapping("/film/type")
@Tag(name = "Type film controller", description = "Handler type film operation")
public class TypeFilmController {
    private final TypeFilmService typeFilmService;

    @Operation(
            summary = "Get type film by id"
    )
    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getTypeFilmById(
            @PathVariable @NotBlank String id
            ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Get type film successfully")
                                .data(typeFilmService.getTypeFilmById(id))
                                .build()
                );
    }

    @Operation(
            summary = "Get all type film in database"
    )
    @GetMapping("/get/all")
    public ResponseEntity<Response> getAllTypeFilm() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Get all type film successfully")
                                .data(typeFilmService.getAllTypeFilm())
                                .build()
                );
    }

    @Operation(
            summary = "Add type film into database"
    )
    @PostMapping("/add")
    public ResponseEntity<Response> addTypeFilm(
            @RequestBody @Valid TypeFilmDto typeFilmDto
            )
    {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.CREATED.value())
                                .message("Add type film successfully")
                                .data(typeFilmService.addTypeFilm(typeFilmDto))
                                .build()
                );
    }

    @Operation(
            summary = "Delete (hidden) type film"
    )
    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteTypeFilm(
            @PathVariable @NotBlank String id
    ){
        typeFilmService.deleteTypeFilm(id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.CREATED.value())
                                .message("Delete (hidden) type film successfully")
                                .build()
                );
    }

    @Operation(
            summary = "Active type film"
    )
    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activeTypeFilm(
            @PathVariable @NotBlank String id
    ){
        typeFilmService.activeTypeFilm(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Active type film successfully")
                                .build()
                );
    }

    @Operation(
            summary = "Update type film into database"
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateTypeFilm(
            @PathVariable @NotBlank String id,
            @RequestBody @Valid TypeFilmDto typeFilmDto
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Update type film successfully")
                                .data(typeFilmService.updateTypeFilm(id, typeFilmDto))
                                .build()
                );
    }
}
