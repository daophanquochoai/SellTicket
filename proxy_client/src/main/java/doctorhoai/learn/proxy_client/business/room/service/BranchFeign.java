package doctorhoai.learn.proxy_client.business.room.service;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.room.model.BranchDto;
import doctorhoai.learn.proxy_client.business.room.service.fallback.BranchFeignFallBack;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "roomservice", contextId = "branchProxyClient", path = "/branch", fallbackFactory = BranchFeignFallBack.class)
public interface BranchFeign {

    @PostMapping("/add")
    public ResponseEntity<Response> addBranch(BranchDto branchDto);

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateBranch(@PathVariable @NotBlank String id, @RequestBody @Valid BranchDto branchDto);

    @GetMapping("/{id}")
    public ResponseEntity<Response> getBranchById(@PathVariable @NotBlank String id);

    @GetMapping("/all")
    public ResponseEntity<Response> getAllBranch();

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteBranch(@PathVariable @NotBlank String id);

    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activateBranch(@PathVariable @NotBlank String id);
}
