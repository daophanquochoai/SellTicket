package doctorhoai.learn.proxy_client.business.user.service;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.user.model.AccountBankingDto;
import doctorhoai.learn.proxy_client.business.user.service.fallback.AccountBankFeignFallBack;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@FeignClient(name = "userservice", contextId = "accountBankProxyClient", path = "/account/bank", fallbackFactory = AccountBankFeignFallBack.class)
//@RibbonClient(name = "userservice") // TODO: deploy k8s
public interface AccountBankFeign {
    @PostMapping("/add")
    public ResponseEntity<Response> addAccountBank(
            @Valid @RequestBody AccountBankingDto accountBankingDto
    );

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateAccountBank(
            @Valid @PathVariable @NotNull @NotBlank String id,
            @RequestBody AccountBankingDto accountBankingDto
    );

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteAccountBank(
            @Valid @PathVariable @NotNull @NotBlank String id
    );
    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activateAccountBank(
            @Valid @PathVariable @NotNull @NotBlank String id
    );
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Response> getAccountBankByCustomerId(
            @Valid @PathVariable @NotNull @NotBlank String customerId
    );
}
