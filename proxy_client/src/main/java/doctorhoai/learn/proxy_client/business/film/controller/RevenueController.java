package doctorhoai.learn.proxy_client.business.film.controller;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.film.service.RevenueFeign;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/film-service/api/revenue")
public class RevenueController {
    private final RevenueFeign revenueFeign;

    @GetMapping("/film/{filmId}")
    public ResponseEntity<Response> getRevenueFilm(
            @PathVariable @Valid @NotBlank String filmId
    ){
        return revenueFeign.getRevenueFilm(filmId);
    }
    @GetMapping("/film/all/{month}/{year}")
    public ResponseEntity<Response> getRevenueFilmAll(
            @PathVariable @Valid @Min(1) @Max(12) Integer month,
            @PathVariable @Valid Integer year
    ){
        return revenueFeign.getRevenueFilmAll(month, year);
    }
}
