package doctorhoai.learn.proxy_client.business.payment.service;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.payment.service.fallback.BillChairFeinFallBack;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "paymentservice",
        contextId = "billChairProxyService",
        path = "/billchair",
        fallbackFactory = BillChairFeinFallBack.class
)
public interface BillChairFeign {

    @GetMapping("/{filmShowId}")
    public ResponseEntity<Response> getBillChair(
            @PathVariable @Valid @NotNull Integer filmShowId
    );
}
