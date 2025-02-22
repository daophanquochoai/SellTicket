package doctorhoai.learn.paymentservice.service.feign.fallback;

import doctorhoai.learn.paymentservice.dto.response.Response;
import doctorhoai.learn.paymentservice.service.feign.FilmFeign;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class FilmFeignFallBack implements FilmFeign {
    @Override
    public ResponseEntity<Response> getFilmById(String id) {
        return ResponseEntity.status(HttpStatusCode.valueOf(429))
                .body(
                        Response.builder()
                                .statusCode(429)
                                .message("Service not found")
                                .build()
                );
    }
}
