package doctorhoai.learn.proxy_client.business.dish.controller;

import doctorhoai.learn.proxy_client.business.dish.model.TypeDishDto;
import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.dish.service.TypeDishFeign;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dish-service/api/typedish")
public class TypeDishController {

    private final TypeDishFeign typeDishFeign;

    @GetMapping("/{id}")
    public ResponseEntity<Response> getTypeDishById(@PathVariable @NotBlank String id){
        return typeDishFeign.getTypeDishById(id);
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllTypeDish(){
        return typeDishFeign.getAllTypeDish();
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteTypeDish(@PathVariable @NotBlank String id){
        return typeDishFeign.deleteTypeDish(id);
    }

    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activateTypeDish(@PathVariable @NotBlank String id){
        return typeDishFeign.activateTypeDish(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Response> addTypeDish(@RequestBody @Valid TypeDishDto typeDishDto){
        return typeDishFeign.addTypeDish(typeDishDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateTypeDish(@PathVariable @NotBlank String id, @RequestBody @Valid TypeDishDto typeDishDto){
        return typeDishFeign.updateTypeDish(id, typeDishDto);
    }

    @GetMapping("/get")
    public ResponseEntity<Response> getTypeFilmCustom(
            @RequestParam(required = false, defaultValue = "0") String page,
            @RequestParam(required = false, defaultValue = "10") String limit,
            @RequestParam(required = false, defaultValue = "asc") String asc,
            @RequestParam(required = false, defaultValue = "none") String status,
            @RequestParam(required = false, defaultValue = "name") String orderBy,
            @RequestParam(required = false, defaultValue = "") String q
    ){
        return typeDishFeign.getTypeFilmCustom(page,limit,asc,status,orderBy,q);
    }
}
