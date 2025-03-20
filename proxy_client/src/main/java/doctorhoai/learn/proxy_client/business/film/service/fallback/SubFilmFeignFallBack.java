package doctorhoai.learn.proxy_client.business.film.service.fallback;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.film.model.request.SubFilmRequest;
import doctorhoai.learn.proxy_client.business.film.service.SubFilmFeign;
import doctorhoai.learn.proxy_client.security.FunctionCommon;
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
            public ResponseEntity<Response> getSubFilmBySubId(String subId, String page, String limit, String asc) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> addSubFilm(SubFilmRequest subFilmRequest) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> deleteById(String filmId, String subId) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> getAll() {
                return functionCommon.process(cause);
            }
        };
    }
}
