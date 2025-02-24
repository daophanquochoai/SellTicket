package doctorhoai.learn.user_service.controller;

import doctorhoai.learn.user_service.dto.AccountBankingDto;
import doctorhoai.learn.user_service.dto.response.Response;
import doctorhoai.learn.user_service.service.inter.AccountBankService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account/bank")
public class AccountBankController {
    private final AccountBankService accountBankService;

    @PostMapping("/add")
    public ResponseEntity<Response> addAccountBank(
            @Valid @RequestBody AccountBankingDto accountBankingDto
            )
    {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.CREATED.value())
                                .message("Create Account Bank Successfully")
                                .data(accountBankService.addAccountBanking(accountBankingDto))
                                .build()
                );
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Response> getAccountBankByCustomerId(
            @Valid @PathVariable @NotNull @NotBlank String customerId
    ){
            return ResponseEntity.ok(
                    Response.builder()
                            .statusCode(HttpStatus.OK.value())
                            .message("Get Account Bank Successfully")
                            .data(accountBankService.getAccountBanking(customerId))
                            .build()
            );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateAccountBank(
            @Valid @PathVariable @NotNull @NotBlank String id,
            @RequestBody AccountBankingDto accountBankingDto
    ){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Update Account Bank Successfully")
                        .data(accountBankService.updateAccountBanking(id, accountBankingDto))
                        .build()
        );
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteAccountBank(
            @Valid @PathVariable @NotNull @NotBlank String id
    ){
        accountBankService.deleteAccountBanking(id);
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Delete Account Bank Successfully")
                        .build()
        );
    }
    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activateAccountBank(
            @Valid @PathVariable @NotNull @NotBlank String id
    ){
        accountBankService.activeAccountBanking(id);
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Activate Account Bank Successfully")
                        .build()
        );
    }
}
