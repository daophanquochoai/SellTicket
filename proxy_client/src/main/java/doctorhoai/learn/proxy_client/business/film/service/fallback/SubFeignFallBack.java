package doctorhoai.learn.proxy_client.business.film.service.fallback;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.film.model.SubDto;
import doctorhoai.learn.proxy_client.business.film.model.request.SubRequest;
import doctorhoai.learn.proxy_client.business.film.service.SubFeign;
import doctorhoai.learn.proxy_client.security.FunctionCommon;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubFeignFallBack implements FallbackFactory<SubFeign> {

    private final FunctionCommon functionCommon;

    @Override
    public SubFeign create(Throwable cause) {
        return new SubFeign() {
            @Override
            public ResponseEntity<Response> getSubs() {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> getSubs(String page, String limit, String q, String asc, String orderBy) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> addSub(SubRequest sub) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> updateSub(String id, SubRequest sub) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> deleteSub(String id) {
                return functionCommon.process(cause);
            }
        };
    }
}
