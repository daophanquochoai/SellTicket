package doctorhoai.learn.paymentservice.service.feign;

import doctorhoai.learn.paymentservice.dto.response.Response;
import doctorhoai.learn.paymentservice.service.feign.fallback.DishFeignFallBack;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "dishservice",
        contextId ="dishServiceClient",
        path = "/dish",
        fallback = DishFeignFallBack.class
)
public interface DishFeign {

    @GetMapping("/{id}")
    public ResponseEntity<Response> getDishById(@PathVariable @NotBlank String id);

    @GetMapping("/all")
    public ResponseEntity<Response> getAllDish();

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteDish(@PathVariable @NotBlank String id);

    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activateDish(@PathVariable @NotBlank String id);

//    @PostMapping("/add")
//    public ResponseEntity<Response> getAllDish(
//            @RequestBody @Valid DishRequest dishRequest
//    );
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<Response> updateDish(@PathVariable @NotBlank String id,@RequestBody @Valid DishRequest dishRequest);
}
