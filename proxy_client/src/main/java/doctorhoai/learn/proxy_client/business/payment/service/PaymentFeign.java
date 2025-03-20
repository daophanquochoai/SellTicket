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
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

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

    @PutMapping("/delete/{id}")
    public ResponseEntity<Response> deleteBills(@Valid @PathVariable @NotNull String id);

    @PutMapping("/active/{id}")
    public ResponseEntity<Response> activateBill(@Valid @PathVariable @NotNull String id);
    @PostMapping("/payment")
    public ResponseEntity<Map<String, Object>> processPayment(@RequestBody Map<String, Object> paymentRequest);
    @GetMapping("/all")
    public ResponseEntity<Response> getAllBills(
            @RequestParam(required = false, defaultValue = "0") String page,
            @RequestParam(required = false, defaultValue = "10") String limit,
            @RequestParam(required = false, defaultValue = "none") String active,
            @RequestParam(required = false, defaultValue = "timestamp") String orderBy,
            @RequestParam(required = false, defaultValue = "asc") String asc,
            @RequestParam(required = false, defaultValue = "") String q
    );
}
