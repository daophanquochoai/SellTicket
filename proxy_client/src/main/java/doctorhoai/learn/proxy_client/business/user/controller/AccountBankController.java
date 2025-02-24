package doctorhoai.learn.proxy_client.business.user.controller;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.user.model.AccountBankingDto;
import doctorhoai.learn.proxy_client.business.user.service.AccountBankFeign;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-service/api/account/bank")
@RequiredArgsConstructor
public class AccountBankController {

    private final AccountBankFeign accountBankFeign;

    @PostMapping("/add")
    public ResponseEntity<Response> addAccountBank(
            @Valid @RequestBody AccountBankingDto accountBankingDto
    ){
        return accountBankFeign.addAccountBank(accountBankingDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateAccountBank(
            @Valid @PathVariable @NotNull @NotBlank String id,
            @RequestBody AccountBankingDto accountBankingDto
    ){
        return accountBankFeign.updateAccountBank(id, accountBankingDto);
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteAccountBank(
            @Valid @PathVariable @NotNull @NotBlank String id
    ){
        return accountBankFeign.deleteAccountBank(id);
    }

    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activateAccountBank(
            @Valid @PathVariable @NotNull @NotBlank String id
    ){
        return accountBankFeign.activateAccountBank(id);
    }
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Response> getAccountBankByCustomerId(
            @Valid @PathVariable @NotNull @NotBlank String customerId
    ){
        return accountBankFeign.getAccountBankByCustomerId(customerId);
    }
}
