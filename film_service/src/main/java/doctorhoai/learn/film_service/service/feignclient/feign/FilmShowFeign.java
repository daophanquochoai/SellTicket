package doctorhoai.learn.film_service.service.feignclient.feign;

import doctorhoai.learn.film_service.dto.response.Response;
import doctorhoai.learn.film_service.service.feignclient.fallback.FilmShowFallBack;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@FeignClient(name = "showtimeservice", contextId = "showTimeFilmProxyClient", path = "/filmshowtime", fallbackFactory = FilmShowFallBack.class)
public interface FilmShowFeign {

    @GetMapping("/get/{branchId}/{time}")
    public ResponseEntity<Response> getFilmShowTimeByParam(
            @PathVariable @Valid @NotBlank String branchId,
            @PathVariable @Valid @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate time
    );
}
