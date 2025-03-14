package doctorhoai.learn.paymentservice.controller;

import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import doctorhoai.learn.paymentservice.dto.BillDto;
import doctorhoai.learn.paymentservice.dto.response.Response;
import doctorhoai.learn.paymentservice.service.inter.BillService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/bill")
public class BillController {

    private final BillService billService;

    @PostMapping("/add")
    public ResponseEntity<Response> createBill(
            @RequestBody @Valid BillDto billDto
            )
    {

        BillDto bill = billService.createBill(billDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        Response.builder()
                                .statusCode(201)
                                .message("Create Bill Successfully")
                                .data(bill)
                                .build()
                );
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllBills(
            @RequestParam(required = false, defaultValue = "0") String page,
            @RequestParam(required = false, defaultValue = "10") String limit,
            @RequestParam(required = false, defaultValue = "none") String active,
            @RequestParam(required = false, defaultValue = "timestamp") String orderBy,
            @RequestParam(required = false, defaultValue = "asc") String asc,
            @RequestParam(required = false, defaultValue = "") String q
            ){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(200)
                        .message("Get All Bills")
                        .data(billService.getAllBills(page,limit,active,orderBy,asc,q))
                        .build()
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response> getBillById(
            @Valid @PathVariable @NotNull String id
    )
    {
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(200)
                        .message("Get Bill Successfully")
                        .data(billService.getBillById(id))
                        .build()
        );
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteBills(
            @Valid @PathVariable @NotNull String id
    )
    {
        billService.deleteBill(id);
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(200)
                        .message("Delete Bill Successfully")
                        .build()
        );
    }

    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activateBill(
            @Valid @PathVariable @NotNull String id
    )
    {
        billService.activeBill(id);
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(200)
                        .message("Activate Bill Successfully")
                        .build()
        );
    }

    @PostMapping("/payment")
    public ResponseEntity<Map<String, Object>> processPayment(@RequestBody Map<String, Object> paymentRequest) {
        try {
            int amount = (int) paymentRequest.get("amount");
            String paymentMethodId = (String) paymentRequest.get("id");
            String currency = (String) paymentRequest.get("currency");
            String billId = (String) paymentRequest.get("billId");

            // 🔥 Tạo PaymentIntent nhưng CHƯA xác nhận
            Map<String, Object> params = new HashMap<>();
            params.put("amount", amount);
            params.put("currency", currency);
            params.put("description", "Payment ticket");
            params.put("payment_method", paymentMethodId);
            params.put("confirmation_method", "manual"); // Chưa xác nhận ngay
            params.put("capture_method", "manual"); // Chưa trừ tiền ngay

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            // ⏳ Xử lý đặt vé
            boolean bookingSuccess = billService.acceptBill(billId, paymentIntent.getId());

            if (bookingSuccess) {
                // ✅ Nếu đặt vé thành công, xác nhận thanh toán
                paymentIntent = paymentIntent.confirm();
            } else {
                // ❌ Nếu đặt vé thất bại, hủy thanh toán
                paymentIntent.cancel();
            }

            // Trả về kết quả
            Map<String, Object> response = new HashMap<>();
            response.put("message", bookingSuccess ? "Payment successful" : "Booking failed, payment canceled");
            response.put("success", bookingSuccess);
            response.put("payment", Map.of(
                    "id", paymentIntent.getId(),
                    "status", paymentIntent.getStatus(),
                    "amount", paymentIntent.getAmount(),
                    "currency", paymentIntent.getCurrency()
            ));

            return ResponseEntity.ok(response);

        } catch (StripeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Payment failed: " + e.getMessage());
            errorResponse.put("success", false);
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

}
