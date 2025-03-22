package doctorhoai.learn.user_service.controller;

import doctorhoai.learn.user_service.dto.request.AccountCustomer;
import doctorhoai.learn.user_service.dto.request.CustomerRequest;
import doctorhoai.learn.user_service.dto.request.Password;
import doctorhoai.learn.user_service.dto.response.Response;
import doctorhoai.learn.user_service.service.inter.AccountBankService;
import doctorhoai.learn.user_service.service.inter.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
@Tag(name = "Customer controller", description = "Handler customer operation")
public class CustomerController {
    private final CustomerService customerService;
    private final AccountBankService accountBankService;
    private final Environment environment;

    @Operation(
            summary = "Get all customer in database"
    )
    @GetMapping("/all")
    public ResponseEntity<Response> getAllCustomer() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Get all customer successfully")
                                .data(Collections.singletonList(customerService.getAllCustomers()))
                                .build()
                );
    }
    @Operation(
            summary = "Delete (hidden) customer in database"
    )
    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteCustomer(
            @PathVariable @NotBlank String id
            ){
        customerService.deleteCustomer(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Delete hidden customer successfully")
                                .build()
                );
    }
    @Operation(
            summary = "Active (no hidden) customer in database"
    )
    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activeCustomer(
            @PathVariable @NotBlank String id
    ){
        customerService.activeCustomer(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Active customer successfully")
                                .build()
                );
    }

    @Operation(
            summary = "Get customer in database"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Response> getCustomerById(
            @PathVariable @NotBlank String id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Get customer successfully")
                                .data(customerService.getCustomerById(id))
                                .build()
                );
    }

    @Operation(
            summary = "Add customer into database"
    )
    @PostMapping("/add")
    public ResponseEntity<Response> addCustomer(
            @RequestBody @Valid CustomerRequest customerRequest
            ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.CREATED.value())
                                .message("Add customer successfully")
                                .data(customerService.addCustomer(customerRequest))
                                .build()
                );
    }

    @PostMapping("/forget/customer")
    public ResponseEntity<Response> forgetCustomer(
            @RequestBody @Valid @NotBlank String email
    ){

        accountBankService.forgetAccountUser(email);
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("OPT has been sent to email successfully")
                        .build()
        );
    }

    @PostMapping("/change/customer/{opt}/{email}")
    public ResponseEntity<Response> changePassword(
            @RequestBody @Valid @Length(min = 6, message = "Password should 6 characters") String password,
            @PathVariable @Valid @NotBlank @Email String email,
            @PathVariable @Valid @NotBlank @Length(min = 4) String opt
    ){
        accountBankService.changePasswordCustomer(password, opt, email);
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Change password successfully")
                        .build()
        );
    }

    @GetMapping("/get/customer")
    public ResponseEntity<Response> getCustomerByCustom(
            @RequestParam(defaultValue = "10", required = false) String limit,
            @RequestParam(defaultValue = "0", required = false) String page,
            @RequestParam(defaultValue = "", required = false) String q,
            @RequestParam(defaultValue = "asc", required = false) String asc,
            @RequestParam(defaultValue = "none", required = false) String status,
            @RequestParam(defaultValue = "name", required = false) String orderBy
    ){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Get customer successfully")
                        .data(customerService.getCustomerByCustom(page,limit,status,orderBy,asc,q))
                        .build()
        );
    }
    @GetMapping("/enviroment")
    public String getenviroment(){
        return environment.getProperty("HOSTNAME");
    }

    @GetMapping("/info/{username}")
    public ResponseEntity<Response> getInfoAccount(
            @PathVariable @Valid @NotBlank String username
    ){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Get info account successfully")
                        .data(customerService.getCustomerByUsername(username))
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateCustomer(
            @PathVariable @NotBlank String id,
            @RequestBody @Valid AccountCustomer account
    ){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Update customer successfully")
                        .data(customerService.updateCustomer(id,account))
                        .build()
        );
    }

    @PutMapping("/update/password/{id}")
    public ResponseEntity<Response> updatePassword(
            @PathVariable @Valid @NotBlank String id,
            @RequestBody @Valid Password password
            ){
        customerService.updatePassword(id, password.getPasswordNew());
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Update password successfully")
                        .build()
        );
    }

}
