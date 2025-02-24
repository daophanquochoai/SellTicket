package doctorhoai.learn.proxy_client.business.payment.controller;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.payment.model.BillDto;
import doctorhoai.learn.proxy_client.business.payment.service.PaymentFeign;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/payment-service/api/bill")
@RequiredArgsConstructor
public class BillController {

    private final PaymentFeign paymentFeign;

    @PostMapping("/add")
    public ResponseEntity<Response> createBill(
            @RequestBody @Valid BillDto billDto
    )
    {
        return paymentFeign.createBill(billDto);
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllBills(){
        return paymentFeign.getAllBills();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response> getBillById(
            @Valid @PathVariable @NotNull String id
    )
    {
        return paymentFeign.getBillById(id);
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteBills(
            @Valid @PathVariable @NotNull String id
    )
    {
        return paymentFeign.deleteBills(id);
    }

    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activateBill(
            @Valid @PathVariable @NotNull String id
    )
    {
        return paymentFeign.activateBill(id);
    }
}
