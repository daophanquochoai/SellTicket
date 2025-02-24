package doctorhoai.learn.proxy_client.business.rate.controller;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.rate.model.request.RateFilmRequest;
import doctorhoai.learn.proxy_client.business.rate.service.RateFeign;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rate-service/api/rate")
@RequiredArgsConstructor
public class RateController {

    private final RateFeign rateFeign;
    @PostMapping("/add/{userid}/{filmId}")
    public ResponseEntity<Response> addRate(@PathVariable @NotBlank String userid, @PathVariable @NotBlank String filmId, @RequestBody @Valid RateFilmRequest rate){
        return rateFeign.addRate(userid, filmId, rate);
    }

    @GetMapping("/film/{filmId}")
    public ResponseEntity<Response> getRateByFilmId(@PathVariable @NotBlank String filmId){
        return rateFeign.getRateByFilmId(filmId);
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteRate(@PathVariable @NotBlank String id){
        return rateFeign.deleteRate(id);
    }

    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activeRate(@PathVariable @NotBlank String id){
        return rateFeign.activeRate(id);
    }
}
