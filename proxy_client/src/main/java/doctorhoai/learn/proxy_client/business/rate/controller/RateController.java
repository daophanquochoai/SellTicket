package doctorhoai.learn.proxy_client.business.rate.controller;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.rate.model.request.RateFilmRequest;
import doctorhoai.learn.proxy_client.business.rate.service.RateFeign;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Response> getRateByFilmId(
            @PathVariable @NotBlank String filmId,
            @RequestParam(defaultValue = "0", required = false) String page,
            @RequestParam(defaultValue = "2", required = false) String limit,
            @RequestParam(defaultValue = "asc", required = false) String asc,
            @RequestParam(defaultValue = "", required = false) String q,
            @RequestParam(defaultValue = "timeStamp", required = false) String orderBy,
            @RequestParam(defaultValue = "ACTIVE", required = false) String status
    ){
        return rateFeign.getRateByFilmId(filmId, page, limit, asc, q, orderBy, status);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<Response> deleteRate(@PathVariable @NotBlank String id){
        return rateFeign.deleteRate(id);
    }

    @PutMapping("/active/{id}")
    public ResponseEntity<Response> activeRate(@PathVariable @NotBlank String id){
        return rateFeign.activeRate(id);
    }
    @GetMapping("/get/rate")
    public ResponseEntity<Response> getRate(
            @RequestParam(defaultValue = "0", required = false) String page,
            @RequestParam(defaultValue = "10", required = false) String limit,
            @RequestParam(defaultValue = "", required = false) String q,
            @RequestParam(defaultValue = "asc", required = false) String asc,
            @RequestParam(defaultValue = "timeStamp",required = false) String orderBy,
            @RequestParam(defaultValue = "none", required = false) String status
    ){
        return rateFeign.getRate(page, limit, q, asc, orderBy, status);
    }
}
