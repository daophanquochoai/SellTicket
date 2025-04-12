package doctorhoai.learn.proxy_client.business.payment.controller;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.payment.model.BillDto;
import doctorhoai.learn.proxy_client.business.payment.service.PaymentFeign;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

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
        System.out.println(123);
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
    @PostMapping("/payment")
    public ResponseEntity<Response> processPayment(@RequestBody Map<String, Object> paymentRequest){
        return paymentFeign.processPayment(paymentRequest);
    }
    @GetMapping("/custom")
    public ResponseEntity<Response> getAllBills(
            @RequestParam(required = false, defaultValue = "0") String page,
            @RequestParam(required = false, defaultValue = "10") String limit,
            @RequestParam(required = false, defaultValue = "none") String active,
            @RequestParam(required = false, defaultValue = "timestamp") String orderBy,
            @RequestParam(required = false, defaultValue = "asc") String asc,
            @RequestParam(required = false, defaultValue = "") String q
    ){
        return paymentFeign.getAllBills(page, limit, active, orderBy, asc, q);
    }

    @GetMapping("/get/{customerId}")
    public ResponseEntity<Response> getBillByCustomerId(
            @PathVariable @NotNull String customerId
    ){
        return paymentFeign.getBillByCustomerId(customerId);
    }
}
