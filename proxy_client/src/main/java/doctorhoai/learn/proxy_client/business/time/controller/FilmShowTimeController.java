package doctorhoai.learn.proxy_client.business.time.controller;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.time.model.request.FilmShowRequest;
import doctorhoai.learn.proxy_client.business.time.service.FilmShowTimeFeign;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/filmshowtime")
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

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteFilmShow(@PathVariable @NotNull Integer id){
        return filmShowTimeFeign.deleteFilmShow(id);
    }

    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activeFilmShow(@PathVariable @NotNull Integer id){
        return filmShowTimeFeign.activeFilmShow(id);
    }

    @GetMapping("/{roomId}/all")
    public ResponseEntity<Response> getFilmShowByDate(@PathVariable @NotNull String roomId, @RequestParam(required = true) LocalDate date){
        return filmShowTimeFeign.getFilmShowByDate(roomId, date);
    }

}
