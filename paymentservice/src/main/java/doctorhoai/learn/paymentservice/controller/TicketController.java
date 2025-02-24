package doctorhoai.learn.paymentservice.controller;

import doctorhoai.learn.paymentservice.dto.TicketDto;
import doctorhoai.learn.paymentservice.dto.response.Response;
import doctorhoai.learn.paymentservice.service.inter.TicketService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/add")
    public ResponseEntity<Response> addTicket(
            @Valid @RequestBody TicketDto ticketDto
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        Response.builder()
                                .statusCode(HttpStatus.CREATED.value())
                                .message("Create ticket successfully")
                                .data(ticketService.addTicket(ticketDto))
                                .build()
                );
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateTicket(
            @Valid @PathVariable @NotNull @NotBlank String id,
            @Valid @RequestBody TicketDto ticketDto
    )
    {
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Update ticket successfully")
                        .data(ticketService.updateTicket(id, ticketDto))
                        .build()
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response> getTicket(
            @Valid @PathVariable @NotNull @NotBlank String id
    ){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Get ticket successfully")
                        .data(ticketService.getTicket(id))
                        .build()
        );
    }
    @GetMapping("/all")
    public ResponseEntity<Response> getAllTicket(
            @RequestParam( required = false, defaultValue = "10") String limit,
            @RequestParam(required = false, defaultValue = "0") String page,
            @RequestParam(required = false, defaultValue = "none") String active,
            @RequestParam(required = false, defaultValue = "price") String orderBy,
            @RequestParam(required = false, defaultValue = "asc") String asc
    ){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Get ticket successfully")
                        .data(ticketService.getTickets(limit, page, active, orderBy, asc))
                        .build()
        );
    }
    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteTicket(
            @Valid @PathVariable @NotNull @NotBlank String id
    ){
        ticketService.deleteTicket(id);
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Delete ticket successfully")
                        .build()
        );
    }
    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activateTicket(
            @Valid @PathVariable @NotNull @NotBlank String id
    ){
        ticketService.activeTicket(id);
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Activate ticket successfully")
                        .build()
        );
    }
}
