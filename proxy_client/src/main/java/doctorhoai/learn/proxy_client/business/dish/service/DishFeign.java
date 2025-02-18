package doctorhoai.learn.proxy_client.business.dish.service;

import doctorhoai.learn.proxy_client.business.dish.model.request.DishRequest;
import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.dish.service.fallback.DishFeignFallBack;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "dishservice", contextId = "dishServiceClient", path = "/dish", fallbackFactory = DishFeignFallBack.class)
public interface DishFeign {

    @GetMapping("/{id}")
    public ResponseEntity<Response> getDishById(@PathVariable @NotBlank String id);

    @GetMapping("/all")
    public ResponseEntity<Response> getAllDish();

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteDish(@PathVariable @NotBlank String id);

    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activateDish(@PathVariable @NotBlank String id);

    @PostMapping("/add")
    public ResponseEntity<Response> getAllDish(@RequestBody @Valid DishRequest dishRequest);

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateDish(@PathVariable @NotBlank String id, @RequestBody @Valid DishRequest dishRequest);
}
