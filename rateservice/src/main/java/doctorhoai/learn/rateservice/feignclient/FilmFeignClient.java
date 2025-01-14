package doctorhoai.learn.rateservice.feignclient;

import doctorhoai.learn.rateservice.dto.response.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient( name = "filmservice", fallback = FilmFeignCallBack.class)
public interface FilmFeignClient {
    @GetMapping("/film/{id}")
    public ResponseEntity<Response> getFilmById(@PathVariable String id);
}
