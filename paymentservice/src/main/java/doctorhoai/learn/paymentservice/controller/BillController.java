package doctorhoai.learn.paymentservice.controller;

import doctorhoai.learn.basedomain.Event.TicketEmail;
import doctorhoai.learn.paymentservice.dto.BillDto;
import doctorhoai.learn.paymentservice.dto.response.Response;
import doctorhoai.learn.paymentservice.service.inter.BillService;
import doctorhoai.learn.paymentservice.service.producer.KafkaMessagePublish;
import doctorhoai.learn.paymentservice.service.producer.KafkaMessagePublish;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bill")
public class BillController {

    private final BillService billService;
    private final KafkaMessagePublish kafkaMessagePublish;

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
    public ResponseEntity<Response> getAllBills(
            @RequestParam(required = false, defaultValue = "0") String page,
            @RequestParam(required = false, defaultValue = "10") String limit,
            @RequestParam(required = false, defaultValue = "none") String active,
            @RequestParam(required = false, defaultValue = "timestamp") String orderBy,
            @RequestParam(required = false, defaultValue = "asc") String asc,
            @RequestParam(required = false, defaultValue = "") String q
            ){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(200)
                        .message("Get All Bills")
                        .data(billService.getAllBills(page,limit,active,orderBy,asc,q))
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
