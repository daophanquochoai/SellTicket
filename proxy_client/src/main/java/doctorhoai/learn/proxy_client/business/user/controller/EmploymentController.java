package doctorhoai.learn.proxy_client.business.user.controller;

import doctorhoai.learn.proxy_client.business.user.model.request.EmployeeRequest;
import doctorhoai.learn.proxy_client.business.user.model.response.Response;
import doctorhoai.learn.proxy_client.business.user.service.EmploymentFeign;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-service/api/employment")
@RequiredArgsConstructor
public class EmploymentController {

    private final EmploymentFeign employmentFeign;

    @GetMapping("/all")
    public ResponseEntity<Response> getAllEmployees(){
        return employmentFeign.getAllEmployees();
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateEmployee(@PathVariable("id") String id, @RequestBody @Valid EmployeeRequest employee){
        return employmentFeign.updateEmployee(id, employee);
    }
    @PostMapping("/add")
    public ResponseEntity<Response> addEmployee(@RequestBody @Valid EmployeeRequest employee){
        return employmentFeign.addEmployee(employee);
    }
    @PutMapping("/delete/{id}")
    public ResponseEntity<Response> hiddenEmployee(@PathVariable("id") @NotBlank String id){
        return employmentFeign.hiddenEmployee(id);
    }
    @PutMapping("/active/{id}")
    public ResponseEntity<Response> activeEmployee(@PathVariable("id") @NotBlank String id){
        return employmentFeign.activeEmployee(id);
    }


}
