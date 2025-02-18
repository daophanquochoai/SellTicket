package doctorhoai.learn.proxy_client.business.user.service;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.user.model.RoleDto;
import doctorhoai.learn.proxy_client.business.user.service.fallback.RoleFeignFallBack;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "userservice",
        contextId = "userClientService",
        path = "/role",
        fallbackFactory = RoleFeignFallBack.class
)
public interface RoleFeign {
    @GetMapping("/all")
    public ResponseEntity<Response> getAllRoles();
    @PostMapping("/add")
    public ResponseEntity<Response> addRole(@RequestBody @Valid RoleDto roleDto);
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteRole(@PathVariable @NotNull int id);
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateRole(@PathVariable int id, @RequestBody @Valid RoleDto roleDto);
}
