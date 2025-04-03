package doctorhoai.learn.rateservice.feignclient;

import doctorhoai.learn.rateservice.dto.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserFeignCallBack implements UserFeignClient{
    @Override
    public ResponseEntity<Response> getCustomerById(String id) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Response.builder()
                        .statusCode(404)
                        .message("Service not found")
                        .build()
        );
    }
}
