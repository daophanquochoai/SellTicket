package doctorhoai.learn.proxy_client.business.room.service;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.room.model.resquest.RoomRequest;
import doctorhoai.learn.proxy_client.business.room.service.fallback.RoomFeignFallBack;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "roomservice", contextId = "roomProxyClient", fallbackFactory = RoomFeignFallBack.class)
public interface RoomFeign {

    @PostMapping("/add")
    ResponseEntity<Response> addRoom(@RequestBody @Valid RoomRequest roomRequest);

    @PutMapping("/update/{id}")
    ResponseEntity<Response> updateRoom(@PathVariable @NotBlank String id, @RequestBody @Valid RoomRequest roomRequest);

    @GetMapping("/{id}")
    ResponseEntity<Response> getRoomById(@PathVariable @NotBlank String id);

    @GetMapping("/all")
    ResponseEntity<Response> getAllRooms();

    @PatchMapping("/delete/{id}")
    ResponseEntity<Response> deleteRoom(@PathVariable @NotBlank String id);

    @PatchMapping("/active/{id}")
    ResponseEntity<Response> activeRoom(@PathVariable @NotBlank String id);
}
