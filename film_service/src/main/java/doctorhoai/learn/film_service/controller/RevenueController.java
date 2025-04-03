package doctorhoai.learn.film_service.controller;

import doctorhoai.learn.film_service.dto.response.Response;
import doctorhoai.learn.film_service.service.inter.RevenueFilmService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/revenue")
@Tag(name = "Revenue Controller", description = "Handler revenue operation")
public class RevenueController {

    private final RevenueFilmService revenueFilmService;

    @GetMapping("/film/{filmId}")
    public ResponseEntity<Response> getRevenueFilm(
            @PathVariable @Valid @NotBlank String filmId
    ){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(200)
                        .message("Get revenue film successfully")
                        .data(revenueFilmService.getRevenueByFilmId(filmId))
                        .build()
        );
    }
    @GetMapping("/film/all/{month}/{year}")
    public ResponseEntity<Response> getRevenueFilmAll(
            @PathVariable @Valid @Min(1) @Max(12) Integer month,
            @PathVariable @Valid Integer year
    ){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(200)
                        .message("Get revenue film successfully")
                        .data(revenueFilmService.getRevenueInFilmAll(month, year))
                        .build()
        );
    }
}
