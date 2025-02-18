package doctorhoai.learn.proxy_client.business.film.service;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.film.model.TypeFilmDto;
import doctorhoai.learn.proxy_client.business.film.service.fallback.TypeFilmFeignFallBack;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "filmservice", contextId = "typeFilmServiceClient2", path = "/film/type", fallbackFactory = TypeFilmFeignFallBack.class)
public interface TypeFilmFeign {

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getTypeFilmById(@PathVariable @NotBlank String id);

    @GetMapping("/get/all")
    public ResponseEntity<Response> getAllTypeFilm();

    @PostMapping("/add")
    public ResponseEntity<Response> addTypeFilm(@RequestBody @Valid TypeFilmDto typeFilmDto);

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteTypeFilm(@PathVariable @NotBlank String id);

    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activeTypeFilm(@PathVariable @NotBlank String id);

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateTypeFilm(@PathVariable @NotBlank String id, @RequestBody @Valid TypeFilmDto typeFilmDto);
}
