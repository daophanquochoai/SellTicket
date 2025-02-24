package doctorhoai.learn.user_service.controller;

import doctorhoai.learn.user_service.dto.CustomerDto;
import doctorhoai.learn.user_service.dto.request.CustomerRequest;
import doctorhoai.learn.user_service.dto.response.Response;
import doctorhoai.learn.user_service.repository.CustomerRepository;
import doctorhoai.learn.user_service.service.inter.CustomerService;
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
@RequestMapping("/customer")
@Tag(name = "Customer controller", description = "Handler customer operation")
public class CustomerController { // TODO : chua co banking
    private final CustomerService customerService;

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


}
