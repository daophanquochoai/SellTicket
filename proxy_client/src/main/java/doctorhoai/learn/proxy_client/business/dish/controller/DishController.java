package doctorhoai.learn.proxy_client.business.dish.controller;

import doctorhoai.learn.proxy_client.business.dish.model.request.DishRequest;
import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.dish.service.DishFeign;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dish-service/api/dish")
@RequiredArgsConstructor
public class DishController {

    private final DishFeign dishFeign;

    @GetMapping("/{id}")
    public ResponseEntity<Response> getDishById(@PathVariable @NotBlank String id){
        return dishFeign.getDishById(id);
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllDish(){
        return dishFeign.getAllDish();
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteDish(@PathVariable @NotBlank String id){
        return dishFeign.deleteDish(id);
    }

    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activateDish(@PathVariable @NotBlank String id){
        return dishFeign.activateDish(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Response> getAllDish(@RequestBody @Valid DishRequest dishRequest){
        return dishFeign.getAllDish(dishRequest);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateDish(@PathVariable @NotBlank String id, @RequestBody @Valid DishRequest dishRequest){
        return dishFeign.updateDish(id, dishRequest);
    }
}
