package doctorhoai.learn.paymentservice.service.feign.fallback;

import doctorhoai.learn.paymentservice.dto.response.Response;
import doctorhoai.learn.paymentservice.service.feign.SubFilmFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubFilmFeignFallBack implements FallbackFactory<SubFilmFeign> {

    private final FunctionCommon functionCommon;

    @Override
    public SubFilmFeign create(Throwable cause) {
        return new SubFilmFeign() {
            @Override
            public ResponseEntity<Response> getSubFilmById(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> getSubFilmByFilmIdAndSubId(String filmId, String subId) {
                return functionCommon.process(cause);
            }
        };
    }
}
