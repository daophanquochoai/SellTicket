package doctorhoai.learn.proxy_client.business.film.service.fallback;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.film.model.request.FilmRequest;
import doctorhoai.learn.proxy_client.business.film.service.FilmFeign;
import doctorhoai.learn.proxy_client.security.FunctionCommon;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FilmFeignFallBack implements FallbackFactory<FilmFeign> {

    private final FunctionCommon functionCommon;
    @Override
    public FilmFeign create(Throwable cause) {
        return new FilmFeign() {
            @Override
            public ResponseEntity<Response> getAllFilms() {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> getFilmById(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> addFilm(FilmRequest film) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> updateFilm(String id, FilmRequest film) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> deleteFilm(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> activeFilm(String id) {
                return functionCommon.process(cause);
            }
        };
    }
}
