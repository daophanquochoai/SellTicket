package doctorhoai.learn.proxy_client.business.film.controller;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.film.model.TypeFilmDto;
import doctorhoai.learn.proxy_client.business.film.service.TypeFilmFeign;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/film-service/api/typefilm")
@RequiredArgsConstructor
public class TypeFilmController {

    private final TypeFilmFeign typeFilmFeign;
    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getTypeFilmById(@PathVariable @NotBlank String id){
        return typeFilmFeign.getTypeFilmById(id);
    }

    @GetMapping("/get/all")
    public ResponseEntity<Response> getAllTypeFilm(){
        return typeFilmFeign.getAllTypeFilm();
    }

    @PostMapping("/add")
    public ResponseEntity<Response> addTypeFilm(@RequestBody @Valid TypeFilmDto typeFilmDto){
        return typeFilmFeign.addTypeFilm(typeFilmDto);
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteTypeFilm(
            @PathVariable @NotBlank String id
    ){
        return typeFilmFeign.deleteTypeFilm(id);
    }

    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activeTypeFilm(@PathVariable @NotBlank String id){
        return typeFilmFeign.activeTypeFilm(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateTypeFilm(@PathVariable @NotBlank String id, @RequestBody @Valid TypeFilmDto typeFilmDto){
        return typeFilmFeign.updateTypeFilm(id, typeFilmDto);
    }
    @GetMapping("/get/custom")
    public ResponseEntity<Response> getTypeFilmByCustom(
            @RequestParam(defaultValue = "0", required = false) String page,
            @RequestParam(defaultValue = "10", required = false) String limit,
            @RequestParam(defaultValue = "", required = false) String q,
            @RequestParam(defaultValue = "name", required = false) String orderBy,
            @RequestParam(defaultValue = "asc", required = false) String asc,
            @RequestParam(defaultValue = "none", required = false) String status
    ){
        return typeFilmFeign.getTypeFilmByCustom(page, limit, q, orderBy, asc, status);
    }
}
