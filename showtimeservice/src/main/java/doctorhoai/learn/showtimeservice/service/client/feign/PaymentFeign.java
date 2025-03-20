package doctorhoai.learn.showtimeservice.service.client.feign;

import doctorhoai.learn.showtimeservice.dto.response.Response;
import doctorhoai.learn.showtimeservice.service.client.fallback.PaymentFeignFallBack;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "paymentservice",
        contextId = "paymentProxyServiceClient",
        path = "/bill",
        fallbackFactory = PaymentFeignFallBack.class
)
public interface PaymentFeign {
    @GetMapping("/get/showtime/{filmShowId}")
    public ResponseEntity<Response> getBillByFilmShowId(
            @PathVariable @NotNull Integer filmShowId
    );
}
