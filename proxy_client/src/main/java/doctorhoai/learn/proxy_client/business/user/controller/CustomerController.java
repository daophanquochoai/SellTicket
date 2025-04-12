package doctorhoai.learn.proxy_client.business.user.controller;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.user.model.request.AccountCustomer;
import doctorhoai.learn.proxy_client.business.user.model.request.Constrain.LocalLogin;
import doctorhoai.learn.proxy_client.business.user.model.request.CustomerRequest;
import doctorhoai.learn.proxy_client.business.user.model.request.Password;
import doctorhoai.learn.proxy_client.business.user.service.CustomerFeign;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<Response> addCustomer(@RequestBody @Validated({LocalLogin.class}) CustomerRequest customerRequest){
        return customerFeign.addCustomer(customerRequest);
    }
    @PostMapping("/forget/customer")
    public ResponseEntity<Response> forgetCustomer(String email) {
        return customerFeign.forgetCustomer(email);
    }

    @PostMapping("/change/customer/{opt}/{email}")
    public ResponseEntity<Response> changePassword(
            @RequestBody @Valid @Length(min = 6, message = "Password should 6 characters") String password,
            @PathVariable @Valid @NotBlank @Email String email,
            @PathVariable @Valid @NotBlank @Length(min = 4) String opt
    ){
        System.out.println(email);
        System.out.println(password);
        System.out.println(opt);
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
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateCustomer(
            @PathVariable @NotBlank String id,
            @RequestBody @Valid AccountCustomer account
    ){
        return customerFeign.updateCustomer(id, account);
    }
    @PutMapping("/update/password/{id}")
    public ResponseEntity<Response> updatePassword(
            @PathVariable @Valid @NotBlank String id,
            @RequestBody @Valid Password password
    ){
        return customerFeign.updatePassword(id, password);
    }
}
