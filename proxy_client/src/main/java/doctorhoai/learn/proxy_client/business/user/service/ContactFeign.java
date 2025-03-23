package doctorhoai.learn.proxy_client.business.user.service;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.user.model.ContactDto;
import doctorhoai.learn.proxy_client.business.user.service.fallback.ContactFeignFallBack;
import doctorhoai.learn.proxy_client.business.user.service.fallback.CustomerFeignCallBack;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "userservice",
        contextId = "contactProxyService",
        path = "/contact",
        fallbackFactory = ContactFeignFallBack.class

)
public interface ContactFeign {
    @PostMapping("/add")
    public ResponseEntity<Response> addContact(
            @RequestBody @Valid ContactDto contactDto
    );
    @PutMapping("/check/{id}")
    public ResponseEntity<Response> checkContact(
            @PathVariable Integer id
    );
    @GetMapping("/all")
    public ResponseEntity<Response> getAllContact();
    @GetMapping("/get/contact")
    public ResponseEntity<Response> getContactByCustom(
            @RequestParam(defaultValue = "10", required = false) String limit,
            @RequestParam(defaultValue = "0", required = false) String page,
            @RequestParam(defaultValue = "", required = false) String q,
            @RequestParam(defaultValue = "asc", required = false) String asc,
            @RequestParam(defaultValue = "none", required = false) String status,
            @RequestParam(defaultValue = "name", required = false) String orderBy
    );
}
