package doctorhoai.learn.proxy_client.business.payment.controller;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.payment.service.BillChairFeign;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment-service/api/billchair")
public class BillChairController {

    private final BillChairFeign billChairFeign;

    @GetMapping("/{filmShowId}")
    public ResponseEntity<Response> getBillChair(
            @PathVariable @Valid @NotNull Integer filmShowId
    ) {
        return billChairFeign.getBillChair(filmShowId);
    }
}
