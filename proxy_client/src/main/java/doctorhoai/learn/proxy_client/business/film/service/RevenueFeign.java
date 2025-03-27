package doctorhoai.learn.proxy_client.business.film.service;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.film.service.fallback.RevenueFeignFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "filmservice", contextId = "revenueServiceClient", path = "/revenue", fallbackFactory = RevenueFeignFallBack.class)
public interface RevenueFeign {

    @GetMapping("/film")
    public ResponseEntity<Response> getRevenueFilm();
    @GetMapping("/film/all")
    public ResponseEntity<Response> getRevenueFilmAll();
}
