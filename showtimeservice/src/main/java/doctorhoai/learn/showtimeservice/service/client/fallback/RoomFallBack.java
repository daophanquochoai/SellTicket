package doctorhoai.learn.showtimeservice.service.client.fallback;

import doctorhoai.learn.showtimeservice.dto.response.Response;
import doctorhoai.learn.showtimeservice.service.client.feign.RoomFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomFallBack implements FallbackFactory<RoomFeign> {

    private final FunctionCommon functionCommon;

    @Override
    public RoomFeign create(Throwable cause) {
        return new RoomFeign() {
            @Override
            public ResponseEntity<Response> getRoomById(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> getAllRooms() {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> activeRoom(String id) {
                return functionCommon.process(cause);
            }
        };
    }
}
