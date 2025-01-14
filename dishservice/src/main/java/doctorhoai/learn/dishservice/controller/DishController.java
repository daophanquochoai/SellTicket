package doctorhoai.learn.dishservice.controller;

import doctorhoai.learn.dishservice.dto.request.DishRequest;
import doctorhoai.learn.dishservice.dto.response.Response;
import doctorhoai.learn.dishservice.service.inter.DishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dish")
@RequiredArgsConstructor
@Tag(name="Dish controller", description = "Handler dish operation")
public class DishController {

    private final DishService dishService;

    @Operation(
            summary = "Get dish by id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Response> getDishById(@PathVariable @NotBlank String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Get dish successfully")
                                .data(dishService.getDishById(id))
                                .build()
                );
    }

    @Operation(
            summary = "Get all dish in database"
    )
    @GetMapping("/all")
    public ResponseEntity<Response> getAllDish() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Get all dish successfully")
                                .data(dishService.getAllDish())
                                .build()
                );
    }

    @Operation(
            summary = "Delete (hidden) dish"
    )
    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteDish(@PathVariable @NotBlank String id) {
        dishService.deleteDish(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Delete successfully")
                                .build()
                );
    }

    @Operation(
            summary = "Active dish"
    )
    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activateDish(@PathVariable @NotBlank String id) {
        dishService.activeDish(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Active successfully")
                                .build()
                );
    }

    @Operation(
            summary = "Add dish"
    )
    @PostMapping("/add")
    public ResponseEntity<Response> getAllDish(
            @RequestBody @Valid DishRequest dishRequest
            )
    {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.CREATED.value())
                                .message("Add successfully")
                                .data(dishService.addDish(dishRequest))
                                .build()
                );
    }

    @Operation(
            summary = "Update dish"
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateDish(
        @PathVariable @NotBlank String id,
        @RequestBody @Valid DishRequest dishRequest
    )
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Update successfully")
                                .data(dishService.updateDish(id, dishRequest))
                                .build()
                );
    }


}
