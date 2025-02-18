package doctorhoai.learn.proxy_client.business.room.service.fallback;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.room.model.ChairDto;
import doctorhoai.learn.proxy_client.business.room.service.ChairFeign;
import doctorhoai.learn.proxy_client.security.FunctionCommon;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChairFeignFallBack implements FallbackFactory<ChairFeign> {

    private final FunctionCommon functionCommon;

    @Override
    public ChairFeign create(Throwable cause) {
        return new ChairFeign() {
            @Override
            public ResponseEntity<Response> getChairById(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> addChair(ChairDto chairDto) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> updateChair(String id, ChairDto chairDto) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> getAllChair() {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> deleteChair(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> activateChair(String id) {
                return functionCommon.process(cause);
            }
        };
    }
}
