package doctorhoai.learn.proxy_client.business.film.controller;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.film.model.request.SubFilmRequest;
import doctorhoai.learn.proxy_client.business.film.service.SubFilmFeign;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/film-service/api/subfilm")
public class SubFilmController {
    private final SubFilmFeign subFilmFeign;

    @GetMapping("/get/sub/{subId}")
    public ResponseEntity<Response> getSubFilmBySubId(
            @PathVariable @Valid @NotBlank String subId,
            @RequestParam(defaultValue = "0", required = false) String page,
            @RequestParam(defaultValue = "10", required = false) String limit,
            @RequestParam(defaultValue = "asc", required = false) String asc
    ){
        return subFilmFeign.getSubFilmBySubId(subId, page, limit, asc);
    }

    @PostMapping("/add")
    public ResponseEntity<Response> addSubFilm(
            @RequestBody @Valid SubFilmRequest subFilmRequest
    ){
        return subFilmFeign.addSubFilm(subFilmRequest);
    }
    @DeleteMapping("/delete/{filmId}/{subId}")
    public ResponseEntity<Response> deleteById(
            @PathVariable @Valid @NotBlank String filmId,
            @PathVariable @Valid @NotBlank String subId
    ){
       return subFilmFeign.deleteById(filmId, subId);
    }
    @GetMapping("/get/all")
    public ResponseEntity<Response> getAll(){
        return subFilmFeign.getAll();
    }
}
