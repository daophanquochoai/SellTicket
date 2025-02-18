package doctorhoai.learn.showtimeservice.service.client.feign;

import doctorhoai.learn.showtimeservice.dto.response.Response;
import doctorhoai.learn.showtimeservice.service.client.fallback.RoomFallBack;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient( name = "roomservice", contextId = "roomServiceClient", fallbackFactory = RoomFallBack.class, path = "/room")
public interface RoomFeign {

//    @PostMapping("/add")
//    public ResponseEntity<Response> addRoom(
//            @RequestBody @Valid RoomRequest roomRequest
//    );
//
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<Response> updateRoom(
//            @PathVariable @NotBlank String id,
//            @RequestBody @Valid RoomRequest roomRequest
//    );

    @GetMapping("/{id}")
    public ResponseEntity<Response> getRoomById(@PathVariable @NotBlank String id);

    @GetMapping("/all")
    public ResponseEntity<Response> getAllRooms();

//    @PatchMapping("/delete/{id}")
//    public ResponseEntity<Response> deleteRoom(
//            @PathVariable @NotBlank String id
//    );

    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activeRoom(
            @PathVariable @NotBlank String id
    );
}
