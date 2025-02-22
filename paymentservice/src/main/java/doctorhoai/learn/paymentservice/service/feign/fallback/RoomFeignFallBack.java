package doctorhoai.learn.paymentservice.service.feign.fallback;

import doctorhoai.learn.paymentservice.dto.response.Response;
import doctorhoai.learn.paymentservice.service.feign.RoomFeign;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class RoomFeignFallBack implements RoomFeign {
    @Override
    public ResponseEntity<Response> getRoomById(String id) {
        return ResponseEntity.status(HttpStatusCode.valueOf(429))
                .body(
                        Response.builder()
                                .statusCode(429)
                                .message("Service Not Found")
                                .build()
                );
    }
}
