package doctorhoai.learn.proxy_client.business.user.service;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.user.model.request.CustomerRequest;
import doctorhoai.learn.proxy_client.business.user.service.fallback.CustomerFeignCallBack;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(
        name = "userservice",
        contextId = "customerClientService",
        path = "/customer",
        fallbackFactory = CustomerFeignCallBack.class

)
public interface CustomerFeign {
    @GetMapping("/all")
    public ResponseEntity<Response> getAllCustomer();
    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteCustomer(@PathVariable @NotBlank String id);
    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activeCustomer(@PathVariable @NotBlank String id);
    @GetMapping("/{id}")
    public ResponseEntity<Response> getCustomerById(@PathVariable @NotBlank String id);
    @PostMapping("/add")
    public ResponseEntity<Response> addCustomer(@RequestBody @Valid CustomerRequest customerRequest);
    @PostMapping("/forget/customer")
    public ResponseEntity<Response> forgetCustomer(
            @RequestBody @Valid @NotBlank String email
    );

    @PostMapping("/change/customer/{opt}/{email}")
    public ResponseEntity<Response> changePassword(
            @RequestBody @Valid @Length(min = 6, message = "Password should 6 characters") String password,
            @PathVariable @Valid @NotBlank @Email String email,
            @PathVariable @Valid @NotBlank @Length(min = 4) String opt
    );

    @GetMapping("/get/customer")
    public ResponseEntity<Response> getCustomerByCustom(
            @RequestParam(defaultValue = "10", required = false) String limit,
            @RequestParam(defaultValue = "0", required = false) String page,
            @RequestParam(defaultValue = "", required = false) String q,
            @RequestParam(defaultValue = "asc", required = false) String asc,
            @RequestParam(defaultValue = "none", required = false) String status,
            @RequestParam(defaultValue = "name", required = false) String orderBy
    );
    @GetMapping("/enviroment")
    public String getenviroment();

    @GetMapping("/info/{username}")
    public ResponseEntity<Response> getInfoAccount(
            @PathVariable @Valid @NotBlank String username
    );

}
