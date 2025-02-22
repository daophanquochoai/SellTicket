package doctorhoai.learn.paymentservice.service.feign;

import doctorhoai.learn.paymentservice.dto.response.Response;
import doctorhoai.learn.paymentservice.service.feign.fallback.RoomFeignFallBack;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "roomservice", contextId = "paymentRoomServiceClient", path = "/room", fallback = RoomFeignFallBack.class)
public interface RoomFeign {
    @GetMapping("/{id}")
    public ResponseEntity<Response> getRoomById(@PathVariable @NotBlank String id);
}
