package doctorhoai.learn.proxy_client.business.dish.service;

import doctorhoai.learn.proxy_client.business.dish.model.TypeDishDto;
import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.dish.service.fallback.TypeFIlmFeignFallBack;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "dishservice", contextId = "typeFilmServiceClient", path = "/typedish", fallbackFactory = TypeFIlmFeignFallBack.class)
public interface TypeDishFeign {
    @GetMapping("/{id}")
    public ResponseEntity<Response> getTypeDishById(@PathVariable @NotBlank String id);

    @GetMapping("/all")
    public ResponseEntity<Response> getAllTypeDish();

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteTypeDish(@PathVariable @NotBlank String id);

    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activateTypeDish(@PathVariable @NotBlank String id);

    @PostMapping("/add")
    public ResponseEntity<Response> addTypeDish(@RequestBody @Valid TypeDishDto typeDishDto);

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateTypeDish(@PathVariable @NotBlank String id, @RequestBody @Valid TypeDishDto typeDishDto);
}
