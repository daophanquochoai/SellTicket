package doctorhoai.learn.proxy_client.business.film.service.fallback;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.film.service.RevenueFeign;
import doctorhoai.learn.proxy_client.security.FunctionCommon;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RevenueFeignFallBack implements FallbackFactory<RevenueFeign> {

    private final FunctionCommon functionCommon;

    @Override
    public RevenueFeign create(Throwable cause) {
        return new RevenueFeign() {
            @Override
            public ResponseEntity<Response> getRevenueFilm(String filmId) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> getRevenueFilmAll(Integer month, Integer year) {
                return functionCommon.process(cause);
            }
        };
    }
}
