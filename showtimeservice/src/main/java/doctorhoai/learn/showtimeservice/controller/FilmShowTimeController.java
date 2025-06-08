package doctorhoai.learn.showtimeservice.controller;

import doctorhoai.learn.showtimeservice.dto.FilmShowDto;
import doctorhoai.learn.showtimeservice.dto.request.FilmShowRequest;
import doctorhoai.learn.showtimeservice.dto.response.Response;
import doctorhoai.learn.showtimeservice.entity.Status;
import doctorhoai.learn.showtimeservice.service.FilmShowService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/filmshowtime")
public class FilmShowTimeController {
    private final FilmShowService filmShowService;

    @PostMapping("/add")
    public ResponseEntity<Response> addFilmShow(@Valid @RequestBody FilmShowRequest filmShowRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Response.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Create film show time successfully")
                        .data(filmShowService.addFilmShow(filmShowRequest))
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateFilmShow(
            @PathVariable @NotNull Integer id,
            @RequestBody @Valid FilmShowRequest filmShowRequest
    ){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Update film show time successfully")
                        .data(filmShowService.updateFilmShow(id, filmShowRequest))
                        .build()
        );
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<Response> deleteFilmShow(
            @PathVariable @NotNull Integer id
    ){
        filmShowService.deleteFilmShow(id);
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Delete film show successfully")
                        .build()
        );
    }
    @PutMapping("/active/{id}")
    public ResponseEntity<Response> activeFilmShow(
            @PathVariable @NotNull Integer id
    ){
        filmShowService.activeFilmShow(id);
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Active film show successfully")
                        .build()
        );
    }
    @GetMapping("/{roomId}/all")
    public ResponseEntity<Response> getFilmShowByDate(
            @PathVariable @NotNull String roomId,
            @RequestParam(required = true) LocalDate date
    ){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Get film show by date")
                        .data(filmShowService.getFilmShows(roomId,date))
                        .build()
        );
    }

    @GetMapping("/rom/{roomId}/showtime/{id}")
    public ResponseEntity<Response> getFilmShowByRoomAndId(
            @Valid @PathVariable @NotNull String roomId,
            @Valid @PathVariable @NotNull Integer id
    ){
        return ResponseEntity.ok(
                Response
                        .builder()
                        .statusCode(200)
                        .data(filmShowService.getFilmShowByRoomIdAndFilmShowDto(roomId,id))
                        .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getFilmShowTime(
            @Valid @PathVariable @NotNull Integer id
    ){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(200)
                        .data(filmShowService.getFilmShowById(id))
                        .build()
        );
    }

    @GetMapping("/get/{branchId}/{time}/{filmId}/{subId}")
    public ResponseEntity<Response> getFilmShowTimeByParam(
            @PathVariable @Valid @NotBlank String branchId,
            @PathVariable @Valid @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate time,
            @PathVariable @Valid @NotBlank String filmId,
            @PathVariable @Valid @NotBlank String subId
    ){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(200)
                        .message("Get film show by param successful")
                        .data(filmShowService.getFilmShowsByBranch(branchId, time, filmId, subId))
                        .build()
        );
    }

    @GetMapping("/get/sub_film/{subfilmId}")
    public ResponseEntity<Response> getFilmShowBySubFilm(
            @PathVariable @Valid @NotBlank String subfilmId
    ){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(200)
                        .message("Get film show by param successful")
                        .data(filmShowService.getFilmShowBySubFilm(subfilmId))
                        .build()
        );
    }

    @GetMapping("/{roomId}/check/all")
    public ResponseEntity<Response> getFilmShowByRoomAndActive(
            @PathVariable @NotNull String roomId
    ){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Get film show by room and status")
                        .data(filmShowService.getFilmShowByRoomAndStatus(roomId, Status.ACTIVE))
                        .build()
        );
    }

}
