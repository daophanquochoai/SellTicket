package doctorhoai.learn.proxy_client.business.payment.service;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.payment.model.TicketDto;
import doctorhoai.learn.proxy_client.business.payment.service.fallback.TicketFallBack;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.NotNull;

@FeignClient(
        name = "paymentservice",
        contextId = "ticketFeignProxyClientService",
        path = "/ticket",
        fallbackFactory = TicketFallBack.class
)
public interface TicketFeign {

    @GetMapping("/add")
    public ResponseEntity<Response> addTicket(
            @Valid @RequestBody TicketDto ticketDto
    );
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateTicket(
            @Valid @PathVariable @NotNull @NotBlank String id,
            @Valid @RequestBody TicketDto ticketDto
    );
    @GetMapping("/{id}")
    public ResponseEntity<Response> getTicket(
            @Valid @PathVariable @NotNull @NotBlank String id
    );
    @GetMapping("/all")
    public ResponseEntity<Response> getAllTicket(
            @RequestParam( required = false, defaultValue = "10") String limit,
            @RequestParam(required = false, defaultValue = "0") String page,
            @RequestParam(required = false, defaultValue = "none") String active,
            @RequestParam(required = false, defaultValue = "price") String orderBy,
            @RequestParam(required = false, defaultValue = "asc") String asc,
            @RequestParam(required = false, defaultValue = "") String q
    );
    @PatchMapping("/delete/{id}")
    public ResponseEntity<Response> deleteTicket(
            @Valid @PathVariable @NotNull @NotBlank String id
    );
    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activateTicket(
            @Valid @PathVariable @NotNull @NotBlank String id
    );
}
