package doctorhoai.learn.paymentservice.service.feign;

import doctorhoai.learn.paymentservice.dto.response.Response;
import doctorhoai.learn.paymentservice.service.feign.fallback.FilmFeignFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "filmservice",
        contextId = "paymentFilmServiceClient",
        path = "/film",
        fallback = FilmFeignFallBack.class
)
public interface FilmFeign {
    @GetMapping("/{id}")
    public ResponseEntity<Response> getFilmById(@PathVariable String id);
}
