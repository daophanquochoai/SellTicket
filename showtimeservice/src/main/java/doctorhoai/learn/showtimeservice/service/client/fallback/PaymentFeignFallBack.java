package doctorhoai.learn.showtimeservice.service.client.fallback;

import doctorhoai.learn.showtimeservice.dto.response.Response;
import doctorhoai.learn.showtimeservice.service.client.feign.PaymentFeign;
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
            public ResponseEntity<Response> getBillByFilmShowId(Integer filmShowId) {
                return functionCommon.process(cause);
            }
        };
    }
}
