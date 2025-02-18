package doctorhoai.learn.proxy_client.business.time.service.fallback;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.time.model.request.FilmShowRequest;
import doctorhoai.learn.proxy_client.business.time.service.FilmShowTimeFeign;
import doctorhoai.learn.proxy_client.security.FunctionCommon;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class FilmShowTimeFeignFallBack implements FallbackFactory<FilmShowTimeFeign> {
    private final FunctionCommon functionCommon;
    @Override
    public FilmShowTimeFeign create(Throwable cause) {
        return new FilmShowTimeFeign() {
            @Override
            public ResponseEntity<Response> addFilmShow(FilmShowRequest filmShowRequest) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> updateFilmShow(Integer id, FilmShowRequest filmShowRequest) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> deleteFilmShow(Integer id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> activeFilmShow(Integer id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> getFilmShowByDate(String roomId, LocalDate date) {
                return functionCommon.process(cause);
            }
        };
    }
}
