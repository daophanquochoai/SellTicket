package doctorhoai.learn.proxy_client.business.dish.service.fallback;

import doctorhoai.learn.proxy_client.business.dish.model.request.DishRequest;
import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.dish.service.DishFeign;
import doctorhoai.learn.proxy_client.security.FunctionCommon;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DishFeignFallBack implements FallbackFactory<DishFeign> {

    private final FunctionCommon functionCommon;
    @Override
    public DishFeign create(Throwable cause) {
        return new DishFeign() {
            @Override
            public ResponseEntity<Response> getDishById(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> getAllDish() {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> deleteDish(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> activateDish(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> getAllDish(DishRequest dishRequest) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> updateDish(String id, DishRequest dishRequest) {
                return functionCommon.process(cause);
            }
        };
    }
}
