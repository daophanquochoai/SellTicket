package doctorhoai.learn.proxy_client.business.payment.service.fallback;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.payment.model.BillDto;
import doctorhoai.learn.proxy_client.business.payment.service.PaymentFeign;
import doctorhoai.learn.proxy_client.security.FunctionCommon;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentFeignFallBack implements FallbackFactory<PaymentFeign> {
    private final FunctionCommon functionCommon;
    @Override
    public PaymentFeign create(Throwable cause) {
        return new PaymentFeign() {
            @Override
            public ResponseEntity<Response> createBill(BillDto billDto) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> getAllBills() {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> getBillById(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> deleteBills(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> activateBill(String id) {
                return functionCommon.process(cause);
            }
        };
    }
}
