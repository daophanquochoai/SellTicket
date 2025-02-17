package doctorhoai.learn.proxy_client.business.user.service;

import doctorhoai.learn.proxy_client.business.user.model.request.CustomerRequest;
import doctorhoai.learn.proxy_client.business.user.model.response.Response;
import doctorhoai.learn.proxy_client.business.user.service.fallback.CustomerFeignCallBack;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
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
}
