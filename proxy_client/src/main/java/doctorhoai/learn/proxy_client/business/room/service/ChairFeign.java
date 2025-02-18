package doctorhoai.learn.proxy_client.business.room.service;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.room.model.ChairDto;
import doctorhoai.learn.proxy_client.business.room.service.fallback.ChairFeignFallBack;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "roomservice", contextId = "chairProxyClient", path = "/chair", fallbackFactory = ChairFeignFallBack.class)
public interface ChairFeign {
    @GetMapping("/{id}")
    public ResponseEntity<Response> getChairById(@PathVariable @NotBlank String id);

    @PostMapping("/add")
    public ResponseEntity<Response> addChair(@RequestBody @Valid ChairDto chairDto);

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateChair(@PathVariable @NotBlank String id, @RequestBody @Valid ChairDto chairDto);

    @GetMapping("/all")
    public ResponseEntity<Response> getAllChair();

    @PatchMapping("/delete{id}")
    public ResponseEntity<Response> deleteChair(@PathVariable @NotBlank String id);

    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activateChair(@PathVariable @NotBlank String id);
}
