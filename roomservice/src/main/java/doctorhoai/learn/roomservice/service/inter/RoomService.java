package doctorhoai.learn.roomservice.service.inter;

import doctorhoai.learn.roomservice.dto.RoomDto;
import doctorhoai.learn.roomservice.dto.resquest.RoomRequest;

import java.util.List;

public interface RoomService {
    RoomDto addRoom(RoomRequest roomRequest);
    RoomDto updateRoom(String id,RoomRequest roomRequest);
    RoomDto getRoom(String id);
    List<RoomDto> getRooms();
    void deleteRoom(String id);
    void activeRoom(String id);
}
