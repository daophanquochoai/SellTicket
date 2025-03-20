package doctorhoai.learn.proxy_client.business.film.service;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.film.model.SubDto;
import doctorhoai.learn.proxy_client.business.film.model.request.SubRequest;
import doctorhoai.learn.proxy_client.business.film.service.fallback.SubFeignFallBack;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "filmservice", contextId = "subProxyService", path = "/sub", fallbackFactory = SubFeignFallBack.class)
public interface SubFeign {

    @GetMapping("/all")
    public ResponseEntity<Response> getSubs();
    @GetMapping("/custom")
    public ResponseEntity<Response> getSubs(
            @RequestParam(defaultValue = "0", required = false) String page,
            @RequestParam(defaultValue = "10", required = false) String limit,
            @RequestParam(defaultValue = "", required = false) String q,
            @RequestParam(defaultValue = "asc", required = false) String asc,
            @RequestParam(defaultValue = "name", required = false) String orderBy
    );
    @PostMapping("/add")
    public ResponseEntity<Response> addSub(@RequestBody @Valid SubRequest sub);
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateSub(
            @PathVariable String id,
            @RequestBody SubRequest sub
    );
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteSub(
            @PathVariable String id
    );
}
