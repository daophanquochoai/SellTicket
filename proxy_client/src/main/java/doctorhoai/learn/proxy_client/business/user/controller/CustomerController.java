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
    @PostMapping("/forget/customer")
    public ResponseEntity<Response> forgetCustomer(String email) {
        return customerFeign.forgetCustomer(email);
    }

    @PostMapping("/change/customer/{opt}/{email}")
    public ResponseEntity<Response> changePassword(String password, String email, String opt) {
        return customerFeign.changePassword(password, email, opt);
    }

    @GetMapping("/get/customer")
    public ResponseEntity<Response> getCustomerByCustom(String limit, String page, String q, String asc, String status, String orderBy) {
        return customerFeign.getCustomerByCustom(limit, page, q, asc, status, orderBy);
    }

    @GetMapping("/enviroment")
    public String getenviroment() {
        return customerFeign.getenviroment();
    }

}
