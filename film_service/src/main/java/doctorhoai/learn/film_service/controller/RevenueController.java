package doctorhoai.learn.film_service.controller;

import doctorhoai.learn.film_service.dto.response.Response;
import doctorhoai.learn.film_service.service.inter.RevenueFilmService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/revenue")
@Tag(name = "Revenue Controller", description = "Handler revenue operation")
public class RevenueController {

    private final RevenueFilmService revenueFilmService;

    @GetMapping("/film")
    public ResponseEntity<Response> getRevenueFilm(){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(200)
                        .message("Get revenue film successfully")
                        .data(revenueFilmService.getRevenueInFilm())
                        .build()
        );
    }
    @GetMapping("/film/all")
    public ResponseEntity<Response> getRevenueFilmAll(){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(200)
                        .message("Get revenue film successfully")
                        .data(revenueFilmService.getRevenueInFilmAll())
                        .build()
        );
    }
}
