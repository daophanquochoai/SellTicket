package doctorhoai.learn.user_service.controller;

import doctorhoai.learn.user_service.dto.response.Response;
import doctorhoai.learn.user_service.service.inter.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/num")
    public ResponseEntity<Response> getNumCustomerAndEmployee() {
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(200)
                        .message("Get num successfully")
                        .data(accountService.getNumCustomerAndEmployee())
                        .build()
        );
    }
}
