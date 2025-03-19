package doctorhoai.learn.proxy_client.business.payment.controller;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.payment.model.TicketDto;
import doctorhoai.learn.proxy_client.business.payment.service.TicketFeign;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment-service/api/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketFeign ticketFeign;

    @PostMapping("/add")
    public ResponseEntity<Response> addTicket(
            @Valid @RequestBody TicketDto ticketDto
    ){
        return ticketFeign.addTicket(ticketDto);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateTicket(
            @Valid @PathVariable @NotNull @NotBlank String id,
            @Valid @RequestBody TicketDto ticketDto
    )
    {
        return ticketFeign.updateTicket(id, ticketDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response> getTicket(
            @Valid @PathVariable @NotNull @NotBlank String id
    ){
        return ticketFeign.getTicket(id);
    }
    @GetMapping("/all")
    public ResponseEntity<Response> getAllTicket(
            @RequestParam( required = false, defaultValue = "10") String limit,
            @RequestParam(required = false, defaultValue = "0") String page,
            @RequestParam(required = false, defaultValue = "none") String active,
            @RequestParam(required = false, defaultValue = "price") String orderBy,
            @RequestParam(required = false, defaultValue = "asc") String asc,
            @RequestParam(required = false, defaultValue = "") String q
    ){
        return ticketFeign.getAllTicket(limit, page, active, orderBy, asc,q);
    }
    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteTicket(
            @Valid @PathVariable @NotNull @NotBlank String id
    ){
        return ticketFeign.deleteTicket(id);
    }
    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activateTicket(
            @Valid @PathVariable @NotNull @NotBlank String id
    ){
        return ticketFeign.activateTicket(id);
    }
}
