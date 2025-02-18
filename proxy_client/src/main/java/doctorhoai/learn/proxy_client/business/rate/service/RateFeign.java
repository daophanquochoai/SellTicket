package doctorhoai.learn.proxy_client.business.rate.service;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.rate.model.request.RateFilmRequest;
import doctorhoai.learn.proxy_client.business.rate.service.fallback.RateFeignFallBack;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "rateservice", contextId = "rateServiceClient", path = "/rate", fallbackFactory = RateFeignFallBack.class)
public interface RateFeign {
    @PostMapping("/add/{userid}/{filmId}")
    public ResponseEntity<Response> addRate(@PathVariable @NotBlank String userid, @PathVariable @NotBlank String filmId, @RequestBody @Valid RateFilmRequest rate);

    @GetMapping("/film/{filmId}")
    public ResponseEntity<Response> getRateByFilmId(@PathVariable @NotBlank String filmId);

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteRate(@PathVariable @NotBlank String id);

    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activeRate(@PathVariable @NotBlank String id);
}
