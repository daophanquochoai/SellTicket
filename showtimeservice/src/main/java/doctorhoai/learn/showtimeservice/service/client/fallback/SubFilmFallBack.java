package doctorhoai.learn.showtimeservice.service.client.fallback;

import doctorhoai.learn.showtimeservice.dto.response.Response;
import doctorhoai.learn.showtimeservice.service.client.feign.SubFilmFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubFilmFallBack implements FallbackFactory<SubFilmFeign> {

    private final FunctionCommon functionCommon;
    @Override
    public SubFilmFeign create(Throwable cause) {
        return new SubFilmFeign(){

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
