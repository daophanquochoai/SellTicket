package doctorhoai.learn.proxy_client.business.room.controller;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.room.model.resquest.RoomRequest;
import doctorhoai.learn.proxy_client.business.room.service.RoomFeign;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room-service/api/room")
public class RoomController {

    private final RoomFeign roomFeign;
    @PostMapping("/add")
    ResponseEntity<Response> addRoom(@RequestBody @Valid RoomRequest roomRequest){
        return roomFeign.addRoom(roomRequest);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<Response> updateRoom(@PathVariable @NotBlank String id, @RequestBody @Valid RoomRequest roomRequest){
        return roomFeign.updateRoom(id, roomRequest);
    }

    @GetMapping("/{id}")
    ResponseEntity<Response> getRoomById(@PathVariable @NotBlank String id){
        return roomFeign.getRoomById(id);
    }

    @GetMapping("/all")
    ResponseEntity<Response> getAllRooms(){
        return roomFeign.getAllRooms();
    }

    @PatchMapping("/delete/{id}")
    ResponseEntity<Response> deleteRoom(@PathVariable @NotBlank String id){
        return roomFeign.deleteRoom(id);
    }

    @PatchMapping("/active/{id}")
    ResponseEntity<Response> activeRoom(@PathVariable @NotBlank String id){
        return roomFeign.activeRoom(id);
    }
}
