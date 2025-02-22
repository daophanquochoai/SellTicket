package doctorhoai.learn.proxy_client.business.payment.service;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.payment.model.BillDto;
import doctorhoai.learn.proxy_client.business.payment.service.fallback.PaymentFeignFallBack;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "paymentservice",
        contextId = "paymentProxyServiceClient",
        path = "/bill",
        fallbackFactory = PaymentFeignFallBack.class
)
public interface PaymentFeign {
    @PostMapping("/add")
    public ResponseEntity<Response> createBill(@RequestBody @Valid BillDto billDto);

    @GetMapping("/all")
    public ResponseEntity<Response> getAllBills();
    @GetMapping("/{id}")
    public ResponseEntity<Response> getBillById(@Valid @PathVariable @NotNull String id);

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteBills(@Valid @PathVariable @NotNull String id);

    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activateBill(@Valid @PathVariable @NotNull String id);
}
