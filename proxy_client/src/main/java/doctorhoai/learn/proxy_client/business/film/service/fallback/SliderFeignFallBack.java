package doctorhoai.learn.proxy_client.business.film.service.fallback;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.film.model.SliderDto;
import doctorhoai.learn.proxy_client.business.film.service.SliderFeign;
import doctorhoai.learn.proxy_client.security.FunctionCommon;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SliderFeignFallBack implements FallbackFactory<SliderFeign> {

    private final FunctionCommon functionCommon;
    @Override
    public SliderFeign create(Throwable cause) {
        return new SliderFeign() {

            @Override
            public ResponseEntity<Response> uploadImages(List<SliderDto> list) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> uploadImage(SliderDto list) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> getSlider() {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> removeSlider(Integer id) {
                return functionCommon.process(cause);
            }
        };
    }
}
