package doctorhoai.learn.proxy_client.business.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.user.model.EmployeeDto;
import doctorhoai.learn.proxy_client.business.user.model.request.EmployeeChange;
import doctorhoai.learn.proxy_client.business.user.model.request.EmployeeRequest;
import doctorhoai.learn.proxy_client.business.user.service.EmploymentFeign;
import doctorhoai.learn.proxy_client.jwt.service.TokenService;
import doctorhoai.learn.proxy_client.jwt.util.JwtUtil;
import doctorhoai.learn.proxy_client.security.AuthenticationConfig;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-service/api/employment")
@RequiredArgsConstructor
public class EmploymentController {

    private final EmploymentFeign employmentFeign;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    @GetMapping("/all")
    public ResponseEntity<Response> getAllEmployees(){
        return employmentFeign.getAllEmployees();
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateEmployee(@PathVariable("id") String id, @RequestBody @Valid EmployeeChange employee){
        ResponseEntity<Response> response = employmentFeign.updateEmployee(id, employee);
        if( response.getStatusCode() == HttpStatus.OK){
            ObjectMapper objectMapper = new ObjectMapper();
            EmployeeDto employeeDto = objectMapper.convertValue(response.getBody().getData(), EmployeeDto.class);
            UserDetails userDetails = userDetailsService.loadUserByUsername(employeeDto.getAccount().getUserName());
            String token = jwtUtil.generateToken(userDetails);
            tokenService.saveToken(token, employeeDto.getAccount().getUserName());
            return ResponseEntity.ok(
                    Response.builder()
                            .statusCode(200)
                            .message("Change Info Successfully")
                            .data(token)
                            .build()
            );
        }else{
            return ResponseEntity.badRequest().build();
        }
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
    @PostMapping("/forget/admin")
    public ResponseEntity<Response> forgetAdmin(
            @RequestBody @Valid @NotBlank String email
    ){
        return employmentFeign.forgetAdmin(email);
    }

    @PostMapping("/change/admin/{opt}/{email}")
    public ResponseEntity<Response> changePassword(
            @RequestBody @Valid @Length(min = 6, message = "Password should 6 characters") String password,
            @PathVariable @Valid @NotBlank @Email String email,
            @PathVariable @Valid @NotBlank @Length(min = 4) String opt
    ){
        return employmentFeign.changePassword(password, email, opt);
    }

    @GetMapping("/get/employee")
    public ResponseEntity<Response> getEmployee(
            @RequestParam(required = false, defaultValue = "0") String page,
            @RequestParam(required = false, defaultValue = "10") String limit,
            @RequestParam(required = false, defaultValue = "asc") String asc,
            @RequestParam(required = false, defaultValue = "none") String status,
            @RequestParam(required = false, defaultValue = "name") String orderBy,
            @RequestParam(required = false, defaultValue = "") String q
    ){
        return employmentFeign.getEmployee(page, limit, asc, status, orderBy, q);
    }
    @PutMapping("/reset/{id}")
    public ResponseEntity<Response> resetAccount(
            @PathVariable @Valid @NotBlank String id
    ){
        return employmentFeign.resetAccount(id);
    }

}
