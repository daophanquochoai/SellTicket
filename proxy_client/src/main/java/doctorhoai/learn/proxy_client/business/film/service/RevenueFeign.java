package doctorhoai.learn.proxy_client.business.film.service;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.film.service.fallback.RevenueFeignFallBack;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "filmservice", contextId = "revenueServiceClient", path = "/revenue", fallbackFactory = RevenueFeignFallBack.class)
public interface RevenueFeign {

    @GetMapping("/film/{filmId}")
    public ResponseEntity<Response> getRevenueFilm(
            @PathVariable @Valid @NotBlank String filmId
    );
    @GetMapping("/film/all/{month}/{year}")
    public ResponseEntity<Response> getRevenueFilmAll(
            @PathVariable @Valid @Min(1) @Max(12) Integer month,
            @PathVariable @Valid Integer year
    );
}
