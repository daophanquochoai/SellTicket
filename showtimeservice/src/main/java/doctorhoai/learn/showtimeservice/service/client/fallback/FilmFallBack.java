package doctorhoai.learn.showtimeservice.service.client.fallback;

import doctorhoai.learn.showtimeservice.dto.response.Response;
import doctorhoai.learn.showtimeservice.service.client.feign.FilmFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FilmFallBack implements FallbackFactory<FilmFeign> {

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
