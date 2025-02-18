package doctorhoai.learn.proxy_client.business.film.service.fallback;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.film.model.TypeFilmDto;
import doctorhoai.learn.proxy_client.business.film.service.TypeFilmFeign;
import doctorhoai.learn.proxy_client.security.FunctionCommon;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TypeFilmFeignFallBack implements FallbackFactory<TypeFilmFeign> {

    private final FunctionCommon functionCommon;
    @Override
    public TypeFilmFeign create(Throwable cause) {
        return new TypeFilmFeign() {
            @Override
            public ResponseEntity<Response> getTypeFilmById(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> getAllTypeFilm() {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> addTypeFilm(TypeFilmDto typeFilmDto) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> deleteTypeFilm(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> activeTypeFilm(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> updateTypeFilm(String id, TypeFilmDto typeFilmDto) {
                return functionCommon.process(cause);
            }
        };
    }
}
