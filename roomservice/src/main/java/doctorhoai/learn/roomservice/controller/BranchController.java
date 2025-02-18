package doctorhoai.learn.roomservice.controller;

import doctorhoai.learn.roomservice.dto.BranchDto;
import doctorhoai.learn.roomservice.dto.response.Response;
import doctorhoai.learn.roomservice.service.inter.BranchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/branch")
@Tag(name = "Branch Controller", description = "Handler Branch Operation")
public class BranchController {
    private final BranchService branchService;

    @Operation(
            summary = "Add Branch"
    )
    @PostMapping("/add")
    public ResponseEntity<Response> addBranch(BranchDto branchDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Response.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Add Branch Successfully")
                        .data(branchService.addBranch(branchDto))
                        .build()
        );
    }

    @Operation(
            summary = "Update Branch"
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateBranch(
            @PathVariable @NotBlank String id,
            @RequestBody @Valid BranchDto branchDto
    )
    {
        return ResponseEntity.status(HttpStatus.OK).body(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Update Branch Successfully")
                        .data(branchService.updateBranch(id, branchDto))
                        .build()
        );
    }

    @Operation(
            summary = "Get branch by id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Response> getBranchById(@PathVariable @NotBlank String id){
        return ResponseEntity.status(HttpStatus.OK).body(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Get Branch Successfully")
                        .data(branchService.getBranch(id))
                        .build()
        );
    }

    @Operation(
            summary = "Get all branch"
    )
    @GetMapping("/all")
    public ResponseEntity<Response> getAllBranch(){
        return ResponseEntity.status(HttpStatus.OK).body(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Get All Branch Successfully")
                        .data(branchService.getAllBranch())
                        .build()
        );
    }

    @Operation(
            summary = "Delete (hidden) branch"
    )
    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteBranch(@PathVariable @NotBlank String id){
        branchService.deleteBranch(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Delete Branch Successfully")
                        .build()
        );
    }

    @Operation(
            summary = "Active branch"
    )
    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activateBranch(@PathVariable @NotBlank String id){
        branchService.activeBranch(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Active Branch Successfully")
                        .build()
        );
    }
}
