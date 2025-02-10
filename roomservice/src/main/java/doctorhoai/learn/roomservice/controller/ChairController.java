package doctorhoai.learn.roomservice.controller;

import doctorhoai.learn.roomservice.dto.ChairDto;
import doctorhoai.learn.roomservice.dto.response.Response;
import doctorhoai.learn.roomservice.service.inter.ChairService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Chair controller", description = "Handler Chair Operation")
@RequestMapping("/chair")
public class ChairController {
    private final ChairService chairService;

    @Operation(
            summary = "Get Chair By id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Response> getChairById(@PathVariable @NotBlank String id) {
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Get chair successfully")
                        .data(chairService.getChair(id))
                        .build()
        );
    }

    @Operation(
            summary = "Add chair"
    )
    @PostMapping("/add")
    public ResponseEntity<Response> addChair(
            @RequestBody @Valid ChairDto chairDto
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Response.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Add chair successfully")
                        .data(chairService.addChair(chairDto))
                        .build()
        );
    }

    @Operation(
            summary = "Update chair"
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateChair(
            @PathVariable @NotBlank String id,
            @RequestBody @Valid ChairDto chairDto
    )
    {
        return ResponseEntity.status(HttpStatus.OK).body(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Update chair successfully")
                        .data(chairService.updateChair(id, chairDto))
                        .build()
        );
    }

    @Operation(
            summary = "Get all chair"
    )
    @GetMapping("/all")
    public ResponseEntity<Response> getAllChair(){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Get all chair successfully")
                        .data(chairService.getAllChair())
                        .build()
        );
    }

    @Operation(
            summary = "Delete (hidden) chair"
    )
    @PatchMapping("/delete{id}")
    public ResponseEntity<Response> deleteChair(@PathVariable @NotBlank String id) {
        chairService.deleteChair(id);
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Delete chair successfully")
                        .build()
        );
    }

    @Operation(
            summary = "Active chair"
    )
    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activateChair(@PathVariable @NotBlank String id) {
        chairService.activeChair(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Active chair successfully")
                        .build()
        );
    }
}
