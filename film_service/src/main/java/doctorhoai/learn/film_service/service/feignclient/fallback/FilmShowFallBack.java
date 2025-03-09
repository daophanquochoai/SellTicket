package doctorhoai.learn.film_service.service.feignclient.fallback;

import doctorhoai.learn.film_service.dto.response.Response;
import doctorhoai.learn.film_service.service.feignclient.feign.FilmShowFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class FilmShowFallBack implements FallbackFactory<FilmShowFeign> {

    private final FunctionCommon functionCommon;

    @Override
    public FilmShowFeign create(Throwable cause) {
        return new FilmShowFeign() {
            @Override
            public ResponseEntity<Response> getFilmShowTimeByParam(String branchId, LocalDate time) {
                return functionCommon.process(cause);
            }
        };
    }
}
