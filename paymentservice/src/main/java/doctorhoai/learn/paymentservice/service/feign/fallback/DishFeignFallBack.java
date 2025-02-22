package doctorhoai.learn.paymentservice.service.feign.fallback;

import doctorhoai.learn.paymentservice.dto.response.Response;
import doctorhoai.learn.paymentservice.service.feign.DishFeign;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class DishFeignFallBack implements DishFeign{
    @Override
    public ResponseEntity<Response> getDishById(String id) {
        return ResponseEntity.status(HttpStatusCode.valueOf(429))
                .body(
                        Response
                                .builder()
                                .statusCode(429)
                                .message("Dish Service Not Found")
                                .build()
                );
    }

    @Override
    public ResponseEntity<Response> getAllDish() {
        return ResponseEntity.status(HttpStatusCode.valueOf(429))
                .body(
                        Response
                                .builder()
                                .statusCode(429)
                                .message("Dish Service Not Found")
                                .build()
                );
    }

    @Override
    public ResponseEntity<Response> deleteDish(String id) {
        return ResponseEntity.status(HttpStatusCode.valueOf(429))
                .body(
                        Response
                                .builder()
                                .statusCode(429)
                                .message("Dish Service Not Found")
                                .build()
                );
    }

    @Override
    public ResponseEntity<Response> activateDish(String id) {
        return ResponseEntity.status(HttpStatusCode.valueOf(429))
                .body(
                        Response
                                .builder()
                                .statusCode(429)
                                .message("Dish Service Not Found")
                                .build()
                );
    }
}
