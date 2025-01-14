package doctorhoai.learn.user_service.controller;

import doctorhoai.learn.user_service.dto.EmployeeDto;
import doctorhoai.learn.user_service.dto.request.EmployeeRequest;
import doctorhoai.learn.user_service.dto.response.Response;
import doctorhoai.learn.user_service.service.inter.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
@Tag(name = "Employee controller", description = "Handler employee operations")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Operation(
            summary = "Get all employee in database"
    )
    @GetMapping("/all")
    public ResponseEntity<Response> getAllEmployees() {
        return ResponseEntity.status(HttpStatus.OK).body(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Get all employees successfully")
                        .dataList(Collections.singletonList(employeeService.getAllEmployees()))
                        .build()
        );
    }
    @Operation(
            summary = "Update employee by id"
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateEmployee(
            @PathVariable("id") String id,
            @RequestBody @Valid EmployeeRequest employee
            ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Update employee successfully")
                        .data(employeeService.updateEmployee(id,employee))
                        .build()
        );
    }
    @Operation(
            summary = "Add employee into database"
    )
    @PostMapping("/add")
    public ResponseEntity<Response> addEmployee(
            @RequestBody @Valid EmployeeRequest employee
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Response.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Add employee successfully")
                        .data(employeeService.addEmployee(employee))
                        .build()
        );
    }
    @Operation(
            summary = "Hidden account employee"
    )
    @PutMapping("/delete/{id}")
    public ResponseEntity<Response> hiddenEmployee(
            @PathVariable("id") @NotBlank String id
    ){
        employeeService.deleteEmployee(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Hidden account employee successfully")
                        .build()
        );
    }
    @Operation(
            summary = "Hidden account employee"
    )
    @PutMapping("/active/{id}")
    public ResponseEntity<Response> activeEmployee(
            @PathVariable("id") @NotBlank String id
    ){
        employeeService.activeEmployee(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Active account employee successfully")
                        .build()
        );
    }

    // TODO : Doi mat khau chua lam


}
