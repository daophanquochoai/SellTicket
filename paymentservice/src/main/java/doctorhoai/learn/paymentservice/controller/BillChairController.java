package doctorhoai.learn.paymentservice.controller;

import doctorhoai.learn.paymentservice.dto.response.Response;
import doctorhoai.learn.paymentservice.service.inter.BillChairService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/billchair")
@RequiredArgsConstructor
public class BillChairController {

    private final BillChairService billChairService;

    @GetMapping("/{filmShowId}")
    public ResponseEntity<Response> getBillChair(
            @PathVariable @Valid @NotNull Integer filmShowId
            ) {
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(200)
                        .message("Get bill chair successfully")
                        .data(billChairService.getBillChairByTimeAndFilmShowId(filmShowId))
                        .build()
        );
    }
}
