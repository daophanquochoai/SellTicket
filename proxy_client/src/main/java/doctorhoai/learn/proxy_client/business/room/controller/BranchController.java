package doctorhoai.learn.proxy_client.business.room.controller;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.room.model.BranchDto;
import doctorhoai.learn.proxy_client.business.room.service.BranchFeign;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/branch")
public class BranchController {

    private final BranchFeign branchFeign;

    @PostMapping("/add")
    public ResponseEntity<Response> addBranch(BranchDto branchDto){
        return branchFeign.addBranch(branchDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateBranch(@PathVariable @NotBlank String id, @RequestBody @Valid BranchDto branchDto){
        return branchFeign.updateBranch(id, branchDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getBranchById(@PathVariable @NotBlank String id){
        return branchFeign.getBranchById(id);
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllBranch(){
        return branchFeign.getAllBranch();
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteBranch(@PathVariable @NotBlank String id){
        return branchFeign.deleteBranch(id);
    }

    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activateBranch(@PathVariable @NotBlank String id){
        return branchFeign.activateBranch(id);
    }
}
