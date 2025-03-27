package doctorhoai.learn.proxy_client.business.film.controller;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.film.service.RevenueFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/film-service/api/revenue")
public class RevenueController {

    private final RevenueFeign revenueFeign;

    @GetMapping("/film")
    public ResponseEntity<Response> getRevenueFilm(){
        return revenueFeign.getRevenueFilm();
    }
    @GetMapping("/film/all")
    public ResponseEntity<Response> getRevenueFilmAll(){
        return revenueFeign.getRevenueFilmAll();
    }
}
