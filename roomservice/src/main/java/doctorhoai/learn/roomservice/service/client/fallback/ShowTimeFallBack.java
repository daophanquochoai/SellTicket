package doctorhoai.learn.roomservice.service.client.fallback;

import doctorhoai.learn.roomservice.dto.response.Response;
import doctorhoai.learn.roomservice.service.client.feign.ShowTimeFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShowTimeFallBack implements FallbackFactory<ShowTimeFeign> {

    private final FunctionCommon functionCommon;

    @Override
    public ShowTimeFeign create(Throwable cause) {
        return new ShowTimeFeign() {
            @Override
            public ResponseEntity<Response> getFilmShowByRoomAndActive(String roomId) {
                return functionCommon.process(cause);
            }
        };
    }
}
