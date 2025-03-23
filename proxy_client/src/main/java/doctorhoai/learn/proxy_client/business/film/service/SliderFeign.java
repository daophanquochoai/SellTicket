package doctorhoai.learn.proxy_client.business.film.service;


import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.film.model.SliderDto;
import doctorhoai.learn.proxy_client.business.film.service.fallback.SliderFeignFallBack;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "filmservice", contextId = "sliderProxyService", path = "/slider", fallbackFactory = SliderFeignFallBack.class)
public interface SliderFeign {
    @PostMapping("/add")
    public ResponseEntity<Response> uploadImages(
            @RequestBody @Valid List<SliderDto> list
    );
    @PostMapping("/add/slider")
    public ResponseEntity<Response> uploadImage(
            @RequestBody @Valid SliderDto list
    );
    @GetMapping("/get")
    public ResponseEntity<Response> getSlider();
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Response> removeSlider(
            @PathVariable Integer id
    );
}
