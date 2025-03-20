package doctorhoai.learn.proxy_client.business.film.service;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.film.model.request.SubFilmRequest;
import doctorhoai.learn.proxy_client.business.film.service.fallback.SubFilmFeignFallBack;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "filmservice", contextId = "subFilmProxyService", path = "/sub/film", fallbackFactory = SubFilmFeignFallBack.class)
public interface SubFilmFeign {
    @GetMapping("/get/sub/{subId}")
    public ResponseEntity<Response> getSubFilmBySubId(
            @PathVariable @Valid @NotBlank String subId,
            @RequestParam(defaultValue = "0", required = false) String page,
            @RequestParam(defaultValue = "10", required = false) String limit,
            @RequestParam(defaultValue = "asc", required = false) String asc
    );
    @PostMapping("/add")
    public ResponseEntity<Response> addSubFilm(
            @RequestBody @Valid SubFilmRequest subFilmRequest
    );
    @DeleteMapping("/delete/{filmId}/{subId}")
    public ResponseEntity<Response> deleteById(
            @PathVariable @Valid @NotBlank String filmId,
            @PathVariable @Valid @NotBlank String subId
    );
    @GetMapping("/get/all")
    public ResponseEntity<Response> getAll();
}
