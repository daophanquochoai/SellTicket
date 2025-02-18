package doctorhoai.learn.proxy_client.business.time.service;


import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.time.model.request.FilmShowRequest;
import doctorhoai.learn.proxy_client.business.time.service.fallback.FilmShowTimeFeignFallBack;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@FeignClient( name = "showtimeservice", contextId = "showTimeProxyClient", path = "/filmshowtime", fallbackFactory = FilmShowTimeFeignFallBack.class)
public interface FilmShowTimeFeign {

    @PostMapping("/add")
    public ResponseEntity<Response> addFilmShow(@Valid @RequestBody FilmShowRequest filmShowRequest);

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateFilmShow(@PathVariable @NotNull Integer id, @RequestBody @Valid FilmShowRequest filmShowRequest);

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteFilmShow(@PathVariable @NotNull Integer id);

    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activeFilmShow(@PathVariable @NotNull Integer id);

    @GetMapping("/{roomId}/all")
    public ResponseEntity<Response> getFilmShowByDate(@PathVariable @NotNull String roomId, @RequestParam(required = true) LocalDate date);
}
