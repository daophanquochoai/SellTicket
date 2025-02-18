package doctorhoai.learn.proxy_client.business.rate.service.fallback;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.rate.model.request.RateFilmRequest;
import doctorhoai.learn.proxy_client.business.rate.service.RateFeign;
import doctorhoai.learn.proxy_client.security.FunctionCommon;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RateFeignFallBack implements FallbackFactory<RateFeign> {

    private final FunctionCommon functionCommon;
    @Override
    public RateFeign create(Throwable cause) {
        return new RateFeign() {
            @Override
            public ResponseEntity<Response> addRate(String userid, String filmId, RateFilmRequest rate) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> getRateByFilmId(String filmId) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> deleteRate(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> activeRate(String id) {
                return functionCommon.process(cause);
            }
        };
    }
}
