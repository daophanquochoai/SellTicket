package doctorhoai.learn.dishservice.controller;

import doctorhoai.learn.dishservice.dto.TypeDishDto;
import doctorhoai.learn.dishservice.dto.response.Response;
import doctorhoai.learn.dishservice.service.inter.TypeDishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/typedish")
@Tag(name = "Type dish controller", description = "Handler type dish operation")
@RequiredArgsConstructor
public class TypeDishController {
    private final TypeDishService typeDishService;

    @Operation(
            summary = "Get type dish by id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Response> getTypeDishById(@PathVariable @NotBlank String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Get type dish successfully")
                                .data(typeDishService.getTypeDishById(id))
                                .build()
                );
    }

    @Operation(
            summary = "Get all type dish in database"
    )
    @GetMapping("/all")
    public ResponseEntity<Response> getAllTypeDish() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Get all type dish in database")
                                .data(typeDishService.getAllTypeDish())
                                .build()
                );
    }

    @Operation(
            summary = "Delete (hidden) type dish"
    )
    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteTypeDish(@PathVariable @NotBlank String id) {
        typeDishService.deleteTypeDish(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Delete hidden type dish successfully")
                                .build()
                );
    }

    @Operation(
            summary = "Active type dish"
    )
    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activateTypeDish(@PathVariable @NotBlank String id) {
        typeDishService.activeTypeDish(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Active type dish successfully")
                                .build()
                );
    }

    @Operation(
            summary = "Add type dish in database"
    )
    @PostMapping("/add")
    public ResponseEntity<Response> addTypeDish(
            @RequestBody @Valid TypeDishDto typeDishDto
            ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.CREATED.value())
                                .message("Add type dish successfully")
                                .data(typeDishService.addTypeDish(typeDishDto))
                                .build()
                );
    }

    @Operation(
            summary = "Update type dish in database"
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateTypeDish(
            @PathVariable @NotBlank String id,
            @RequestBody @Valid TypeDishDto typeDishDto
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.CREATED.value())
                                .message("Add type dish successfully")
                                .data(typeDishService.updateTypeDish(id,typeDishDto))
                                .build()
                );
    }

}
