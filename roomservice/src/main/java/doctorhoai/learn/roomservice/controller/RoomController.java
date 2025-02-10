package doctorhoai.learn.roomservice.controller;

import doctorhoai.learn.roomservice.dto.response.Response;
import doctorhoai.learn.roomservice.dto.resquest.RoomRequest;
import doctorhoai.learn.roomservice.service.inter.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Tag(name = "Room controller", description = "Handler Room Operation")
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    @Operation(
            summary = "Add room in database"
    )
    @PostMapping("/add")
    public ResponseEntity<Response> addRoom(
            @RequestBody @Valid RoomRequest roomRequest
            )
    {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.CREATED.value())
                                .message("Create Room Successfully")
                                .data(roomService.addRoom(roomRequest))
                                .build()
                );
    }

    @Operation(
            summary = "Update room in database"
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateRoom(
            @PathVariable @NotBlank String id,
            @RequestBody @Valid RoomRequest roomRequest
    ){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Update Room Successfully")
                        .data(roomService.updateRoom(id, roomRequest))
                        .build()
        );
    }

    @Operation(
            summary = "Get room by id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Response> getRoomById(
            @PathVariable @NotBlank String id
    )
    {
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Get Room Successfully")
                        .data(roomService.getRoom(id))
                        .build()
        );
    }

    @Operation(
            summary = "Get all room in database"
    )
    @GetMapping("/all")
    public ResponseEntity<Response> getAllRooms()
    {
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Get All Rooms Successfully")
                        .data(roomService.getRooms())
                        .build()
        );
    }

    @Operation(
            summary = "Delete (hidden) room in database"
    )
    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteRoom(
            @PathVariable @NotBlank String id
    )
    {
        roomService.deleteRoom(id);
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Delete Room Successfully")
                        .build()
        );
    }

    @Operation(
            summary = "activeRoom"
    )
    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activeRoom(
            @PathVariable @NotBlank String id
    )
    {
        roomService.activeRoom(id);
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Active Room Successfully")
                        .build()
        );
    }
}
