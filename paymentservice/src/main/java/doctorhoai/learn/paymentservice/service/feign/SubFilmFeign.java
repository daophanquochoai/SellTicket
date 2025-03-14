package doctorhoai.learn.paymentservice.service.feign;

import doctorhoai.learn.paymentservice.dto.response.Response;
import doctorhoai.learn.paymentservice.service.feign.fallback.FilmFeignFallBack;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "filmservice",
        contextId = "subFilmpaymentClientSerivce",
        path = "/sub/film",
        fallback = FilmFeignFallBack.class
)
public interface SubFilmFeign {
    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getSubFilmById(
            @PathVariable @Valid @NotBlank String id
    );
    @GetMapping("/get/{filmId}/{subId}")
    public ResponseEntity<Response> getSubFilmByFilmIdAndSubId(
            @PathVariable @Valid @NotBlank String filmId,
            @PathVariable @Valid @NotBlank String subId
    );
}
