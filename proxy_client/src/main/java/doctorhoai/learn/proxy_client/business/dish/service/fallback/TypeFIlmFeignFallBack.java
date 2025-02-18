package doctorhoai.learn.proxy_client.business.dish.service.fallback;

import doctorhoai.learn.proxy_client.business.dish.model.TypeDishDto;
import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.dish.service.TypeDishFeign;
import doctorhoai.learn.proxy_client.security.FunctionCommon;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TypeFIlmFeignFallBack implements FallbackFactory<TypeDishFeign> {
    private final FunctionCommon functionCommon;

    @Override
    public TypeDishFeign create(Throwable cause) {
        return new TypeDishFeign() {
            @Override
            public ResponseEntity<Response> getTypeDishById(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> getAllTypeDish() {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> deleteTypeDish(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> activateTypeDish(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> addTypeDish(TypeDishDto typeDishDto) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> updateTypeDish(String id, TypeDishDto typeDishDto) {
                return functionCommon.process(cause);
            }
        };
    }
}
