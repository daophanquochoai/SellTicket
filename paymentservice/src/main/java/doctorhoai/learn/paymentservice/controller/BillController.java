package doctorhoai.learn.paymentservice.controller;

import doctorhoai.learn.paymentservice.dto.BillDto;
import doctorhoai.learn.paymentservice.dto.response.Response;
import doctorhoai.learn.paymentservice.service.inter.BillService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bill")
public class BillController {

    private final BillService billService;

    @PostMapping("/add")
    public ResponseEntity<Response> createBill(
            @RequestBody @Valid BillDto billDto
            )
    {
        BillDto bill = billService.createBill(billDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        Response.builder()
                                .statusCode(201)
                                .message("Create Bill Successfully")
                                .data(bill)
                                .build()
                );
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllBills(){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(200)
                        .message("Get All Bills")
                        .data(billService.getAllBills())
                        .build()
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response> getBillById(
            @Valid @PathVariable @NotNull String id
    )
    {
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(200)
                        .message("Get Bill Successfully")
                        .data(billService.getBillById(id))
                        .build()
        );
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteBills(
            @Valid @PathVariable @NotNull String id
    )
    {
        billService.deleteBill(id);
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(200)
                        .message("Delete Bill Successfully")
                        .build()
        );
    }

    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activateBill(
            @Valid @PathVariable @NotNull String id
    )
    {
        billService.activeBill(id);
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(200)
                        .message("Activate Bill Successfully")
                        .build()
        );
    }
}
