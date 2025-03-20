package doctorhoai.learn.proxy_client.business.time.controller;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.time.model.request.FilmShowRequest;
import doctorhoai.learn.proxy_client.business.time.service.FilmShowTimeFeign;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/filmshowtime-service/api/filmshowtime")
@RequiredArgsConstructor
public class FilmShowTimeController {

    private final FilmShowTimeFeign filmShowTimeFeign;

    @PostMapping("/add")
    public ResponseEntity<Response> addFilmShow(@Valid @RequestBody FilmShowRequest filmShowRequest){
        return filmShowTimeFeign.addFilmShow(filmShowRequest);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateFilmShow(@PathVariable @NotNull Integer id, @RequestBody @Valid FilmShowRequest filmShowRequest){
        return filmShowTimeFeign.updateFilmShow(id, filmShowRequest);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<Response> deleteFilmShow(@PathVariable @NotNull Integer id){
        return filmShowTimeFeign.deleteFilmShow(id);
    }

    @PutMapping("/active/{id}")
    public ResponseEntity<Response> activeFilmShow(@PathVariable @NotNull Integer id){
        return filmShowTimeFeign.activeFilmShow(id);
    }

    @GetMapping("/{roomId}/all")
    public ResponseEntity<Response> getFilmShowByDate(@PathVariable @NotNull String roomId, @RequestParam(required = true) LocalDate date){
        return filmShowTimeFeign.getFilmShowByDate(roomId, date);
    }

    @GetMapping("/get/{branchId}/{time}/{filmId}/{subId}")
    public ResponseEntity<Response> getFilmShowTimeByParam(
            @PathVariable @Valid @NotBlank String branchId,
            @PathVariable @Valid @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate time,
            @PathVariable @Valid @NotBlank String filmId,
            @PathVariable @Valid @NotBlank String subId
    ){
        return filmShowTimeFeign.getFilmShowTimeByParam(branchId, time, filmId, subId);
    }

}
