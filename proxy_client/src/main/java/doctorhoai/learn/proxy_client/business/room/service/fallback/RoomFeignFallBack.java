package doctorhoai.learn.proxy_client.business.room.service.fallback;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.room.model.resquest.RoomRequest;
import doctorhoai.learn.proxy_client.business.room.service.RoomFeign;
import doctorhoai.learn.proxy_client.security.FunctionCommon;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomFeignFallBack implements FallbackFactory<RoomFeign> {

    private final FunctionCommon functionCommon;
    @Override
    public RoomFeign create(Throwable cause) {
        return new RoomFeign() {
            @Override
            public ResponseEntity<Response> addRoom(RoomRequest roomRequest) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> updateRoom(String id, RoomRequest roomRequest) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> getRoomById(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> getAllRooms() {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> deleteRoom(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> activeRoom(String id) {
                return functionCommon.process(cause);
            }
        };
    }
}
