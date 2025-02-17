package doctorhoai.learn.proxy_client.business.user.service;

import doctorhoai.learn.proxy_client.business.user.model.response.Response;
import doctorhoai.learn.proxy_client.business.user.model.request.EmployeeRequest;
import doctorhoai.learn.proxy_client.business.user.service.fallback.EmployeeFeignCallBack;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(
        name = "userservice",
        contextId = "employmentClientService",
        path = "/employee",
        fallbackFactory = EmployeeFeignCallBack.class)
public interface EmploymentFeign {
    @GetMapping("/all")
    public ResponseEntity<Response> getAllEmployees();
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateEmployee(@PathVariable("id") String id, @RequestBody @Valid EmployeeRequest employee);
    @PostMapping("/add")
    public ResponseEntity<Response> addEmployee(@RequestBody @Valid EmployeeRequest employee);
    @PutMapping("/delete/{id}")
    public ResponseEntity<Response> hiddenEmployee(@PathVariable("id") @NotBlank String id);
    @PutMapping("/active/{id}")
    public ResponseEntity<Response> activeEmployee(@PathVariable("id") @NotBlank String id);
}
