package doctorhoai.learn.proxy_client.business.film.service;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.film.model.request.FilmRequest;
import doctorhoai.learn.proxy_client.business.film.service.fallback.FilmFeignFallBack;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@FeignClient(name = "filmservice", contextId = "filmServiceClient", path = "/film", fallbackFactory = FilmFeignFallBack.class)
public interface FilmFeign {

    @GetMapping("/all")
    public ResponseEntity<Response> getAllFilms();

    @GetMapping("/{id}")
    public ResponseEntity<Response> getFilmById(@PathVariable String id);

    @PostMapping("/add")
    public ResponseEntity<Response> addFilm(@RequestBody @Valid FilmRequest film );

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateFilm(@PathVariable @NotBlank String id, @RequestBody @Valid FilmRequest film);

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteFilm(@PathVariable @NotBlank String id);

    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activeFilm(@PathVariable @NotBlank String id);

    @GetMapping("/get/custom")
    public ResponseEntity<Response> getFilmByCustom(
            @RequestParam(defaultValue = "0", required = false) String page,
            @RequestParam(defaultValue = "10", required = false) String limit,
            @RequestParam(defaultValue = "", required = false) String q,
            @RequestParam(defaultValue = "asc", required = false) String asc,
            @RequestParam(defaultValue = "none", required = false) String status,
            @RequestParam(defaultValue = "name", required = false) String orderBy
    );
    @GetMapping("/get/{branchId}/{time}")
    public ResponseEntity<Response> getFilmByBranchIdAndTime(
            @PathVariable @Valid @NotBlank String branchId,
            @PathVariable @Valid @NotNull LocalDate time
    );
    @GetMapping("/get/status/{status}")
    public ResponseEntity<Response> getFilmByStatus(
            @PathVariable @Valid @NotBlank String status
    );
    @GetMapping("/get/sub/{subId}")
    public ResponseEntity<Response> getFilmNotInSub(
            @PathVariable String subId
    );
}
