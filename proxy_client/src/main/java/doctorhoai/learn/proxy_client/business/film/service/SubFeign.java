package doctorhoai.learn.proxy_client.business.film.service;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.film.service.fallback.SubFeignFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "filmservice", contextId = "subProxyService", path = "/sub", fallbackFactory = SubFeignFallBack.class)
public interface SubFeign {

    @GetMapping("/all")
    public ResponseEntity<Response> getSubs();
}
