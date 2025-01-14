package doctorhoai.learn.rateservice.controller;

import doctorhoai.learn.rateservice.dto.request.RateFilmRequest;
import doctorhoai.learn.rateservice.dto.response.Response;
import doctorhoai.learn.rateservice.service.inter.RateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rate")
@Tag(name = "Tag controller", description = "Handler tag operation")
@RequiredArgsConstructor
public class RateController {
    private final RateService rateService;

    @Operation(
            summary = "Add rate by customer id and film id"
    )
    @PostMapping("/add/{userid}/{filmId}")
    public ResponseEntity<Response> addRate(
            @PathVariable @NotBlank String userid,
            @PathVariable @NotBlank String filmId,
            @RequestBody @Valid RateFilmRequest rate
            ){
        rateService.addRateFilm(userid, filmId, rate);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder().build());
    }

    @Operation(
            summary = "Get all rate of film"
    )
    @GetMapping("/film/{filmId}")
    public ResponseEntity<Response> getRateByFilmId(@PathVariable @NotBlank String filmId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Get rate successfully")
                                .data(rateService.getRateByFilmId(filmId))
                                .build()
                );
    }

    @Operation(
            summary = "Delete (hidden) rate"
    )
    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteRate(
            @PathVariable @NotBlank String id
    ){
        rateService.deleteRateFilm(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Delete (hidden) successfully")
                        .build());
    }

    @Operation(
            summary = "Active rate"
    )
    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activeRate(
            @PathVariable @NotBlank String id
    ){
        rateService.activeRateFilm(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Active successfully")
                        .build());
    }
}
