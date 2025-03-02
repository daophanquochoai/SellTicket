package doctorhoai.learn.user_service.controller;

import doctorhoai.learn.user_service.service.inter.AccountBankService;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.core.env.Environment;
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
    private final Environment environment;
    private final AccountBankService accountBankService;

    @Operation(
            summary = "Get all employee in database"
    )
    @GetMapping("/all")
    public ResponseEntity<Response> getAllEmployees() {
        return ResponseEntity.status(HttpStatus.OK).body(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Get all employees successfully")
                        .data(Collections.singletonList(employeeService.getAllEmployees()))
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

    @GetMapping("/event")
    public String getEvent(){
        return environment.getProperty("local.server.port");
    }


    @PostMapping("/forget/admin")
    public ResponseEntity<Response> forgetAdmin(
            @RequestBody @Valid @NotBlank String email
    ){
        accountBankService.forgetAccountAdmin(email);
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("OPT has been sent to email successfully")
                        .build()
        );
    }

    @PostMapping("/change/admin/{opt}/{email}")
    public ResponseEntity<Response> changePassword(
            @RequestBody @Valid @Length(min = 6, message = "Password should 6 characters") String password,
            @PathVariable @Valid @NotBlank @Email String email,
            @PathVariable @Valid @NotBlank @Length(min = 4) String opt
    ){
        accountBankService.changePasswordAdmin(password,email,opt);
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Change password successfully")
                        .build()
        );
    }

    @GetMapping("/get/employee")
    public ResponseEntity<Response> getEmployee(
            @RequestParam(required = false, defaultValue = "0") String page,
            @RequestParam(required = false, defaultValue = "10") String limit,
            @RequestParam(required = false, defaultValue = "asc") String asc,
            @RequestParam(required = false, defaultValue = "none") String status,
            @RequestParam(required = false, defaultValue = "name") String orderBy,
            @RequestParam(required = false, defaultValue = "") String q
    )
    {
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("")
                        .data(employeeService.getEmployee( limit, page, q,asc,status,orderBy))
                        .build()
        );
    }

}
