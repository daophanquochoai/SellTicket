package doctorhoai.learn.proxy_client.business.user.controller;


import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.user.service.AccountFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-service/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountFeign accountFeign;

    @GetMapping("/num")
    public ResponseEntity<Response> getNumCustomerAndEmployee(){
        return accountFeign.getNumCustomerAndEmployee();
    }
}
