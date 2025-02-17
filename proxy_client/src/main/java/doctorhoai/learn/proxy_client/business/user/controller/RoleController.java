package doctorhoai.learn.proxy_client.business.user.controller;

import doctorhoai.learn.proxy_client.business.user.model.RoleDto;
import doctorhoai.learn.proxy_client.business.user.model.response.Response;
import doctorhoai.learn.proxy_client.business.user.service.RoleFeign;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user-service/api/role")
@RestController
@RequiredArgsConstructor
public class RoleController {

    private final RoleFeign roleFeign;

    @GetMapping("/all")
    public ResponseEntity<Response> getAllRole(){
        return roleFeign.getAllRoles();
    }
    @PostMapping("/add")
    public ResponseEntity<Response> addRole(
            @RequestBody @Valid RoleDto roleDto
            ){
        return roleFeign.addRole(roleDto);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteRole(
            @PathVariable @NotNull Integer id
    ){
        return roleFeign.deleteRole(id);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateRole(
            @PathVariable @NotNull Integer id,
            @RequestBody @Valid RoleDto roleDto
    ){
        return roleFeign.updateRole(id, roleDto);
    }
}
