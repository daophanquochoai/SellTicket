package doctorhoai.learn.proxy_client.business.user.service;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.user.model.request.EmployeeChange;
import doctorhoai.learn.proxy_client.business.user.model.request.EmployeeRequest;
import doctorhoai.learn.proxy_client.business.user.service.fallback.EmployeeFeignCallBack;
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
        contextId = "employmentClientService",
        path = "/employee",
        fallbackFactory = EmployeeFeignCallBack.class)
public interface EmploymentFeign {
    @GetMapping("/all")
    public ResponseEntity<Response> getAllEmployees();
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateEmployee(@PathVariable("id") String id, @RequestBody @Valid EmployeeChange employee);
    @PostMapping("/add")
    public ResponseEntity<Response> addEmployee(@RequestBody @Valid EmployeeRequest employee);
    @PutMapping("/delete/{id}")
    public ResponseEntity<Response> hiddenEmployee(@PathVariable("id") @NotBlank String id);
    @PutMapping("/active/{id}")
    public ResponseEntity<Response> activeEmployee(@PathVariable("id") @NotBlank String id);
    @PostMapping("/forget/admin")
    public ResponseEntity<Response> forgetAdmin(
            @RequestBody @Valid @NotBlank String email
    );

    @PostMapping("/change/admin/{opt}/{email}")
    public ResponseEntity<Response> changePassword(
            @RequestBody @Valid @Length(min = 6, message = "Password should 6 characters") String password,
            @PathVariable @Valid @NotBlank @Email String email,
            @PathVariable @Valid @NotBlank @Length(min = 4) String opt
    );

    @GetMapping("/get/employee")
    public ResponseEntity<Response> getEmployee(
            @RequestParam(required = false, defaultValue = "0") String page,
            @RequestParam(required = false, defaultValue = "10") String limit,
            @RequestParam(required = false, defaultValue = "asc") String asc,
            @RequestParam(required = false, defaultValue = "none") String status,
            @RequestParam(required = false, defaultValue = "name") String orderBy,
            @RequestParam(required = false, defaultValue = "") String q
    );
    @GetMapping("/info/{username}")
    public ResponseEntity<Response> getInfoAccount(
            @PathVariable @Valid @NotBlank String username
    );
    @PutMapping("/reset/{id}")
    public ResponseEntity<Response> resetAccount(
            @PathVariable @Valid @NotBlank String id
    );
}
