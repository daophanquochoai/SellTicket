package doctorhoai.learn.user_service.controller;

import doctorhoai.learn.user_service.dto.RoleDto;
import doctorhoai.learn.user_service.dto.response.Response;
import doctorhoai.learn.user_service.service.inter.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/role")
@Tag(name = "Role controllers", description = "Handlers role operations")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @Operation(
            summary = "Get all role in database"
    )
    @GetMapping("/all")
    public ResponseEntity<Response> getAllRoles() {
        List<RoleDto> roleList = roleService.getRoles();
        return ResponseEntity.status(HttpStatus.OK).body(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Get all role successfully")
                        .data(Collections.singletonList(roleList))
                        .build()
        );
    }

    @Operation(
            summary = "Add role in database"
    )
    @PostMapping("/add")
    public ResponseEntity<Response> addRole(
            @RequestBody @Valid RoleDto roleDto
    ){
        RoleDto role = roleService.addRole(roleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Response.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Add role successfully")
                        .data(role)
                        .build()
        );
    }

    @Operation(
            summary = "Delete Role (Hidden) in database"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteRole(
            @PathVariable @NotNull int id
    ){
        roleService.deleteRole(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Delete role successfully")
                        .build()
        );
    }

    @Operation(
            summary = "Update role in database"
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateRole(
            @PathVariable int id,
            @RequestBody @Valid RoleDto roleDto
    ){
        RoleDto r = roleService.updateRole(id, roleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Response.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Add role successfully")
                        .data(r)
                        .build()
        );
    }
}
