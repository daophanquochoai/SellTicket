package doctorhoai.learn.proxy_client.business.user.controller;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.user.model.request.CustomerRequest;
import doctorhoai.learn.proxy_client.business.user.service.CustomerFeign;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-service/api/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerFeign customerFeign;
    @GetMapping("/all")
    public ResponseEntity<Response> getAllCustomer(){
        return customerFeign.getAllCustomer();
    }
    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteCustomer(@PathVariable @NotBlank String id){
        return customerFeign.deleteCustomer(id);
    }
    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activeCustomer(@PathVariable @NotBlank String id){
        return customerFeign.activeCustomer(id);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response> getCustomerById(@PathVariable @NotBlank String id){
        return customerFeign.getCustomerById(id);
    }
    @PostMapping("/add")
    public ResponseEntity<Response> addCustomer(@RequestBody @Valid CustomerRequest customerRequest){
        return customerFeign.addCustomer(customerRequest);
    }
}
