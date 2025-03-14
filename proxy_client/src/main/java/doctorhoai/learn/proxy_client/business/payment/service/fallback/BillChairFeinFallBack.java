package doctorhoai.learn.proxy_client.business.payment.service.fallback;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.payment.service.BillChairFeign;
import doctorhoai.learn.proxy_client.security.FunctionCommon;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BillChairFeinFallBack implements FallbackFactory<BillChairFeign> {

    private final FunctionCommon functionCommon;

    @Override
    public BillChairFeign create(Throwable cause) {
        return new BillChairFeign() {
            @Override
            public ResponseEntity<Response> getBillChair(Integer filmShowId) {
                return functionCommon.process(cause);
            }
        };
    }
}
