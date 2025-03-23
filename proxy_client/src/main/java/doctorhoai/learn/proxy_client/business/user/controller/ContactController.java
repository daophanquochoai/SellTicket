package doctorhoai.learn.proxy_client.business.user.controller;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.user.model.ContactDto;
import doctorhoai.learn.proxy_client.business.user.service.ContactFeign;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-service/api/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactFeign contactFeign;

    @PostMapping("/add")
    public ResponseEntity<Response> addContact(
            @RequestBody @Valid ContactDto contactDto
    ){
        return contactFeign.addContact(contactDto);
    }
    @PutMapping("/check/{id}")
    public ResponseEntity<Response> checkContact(
            @PathVariable Integer id
    ){
        return contactFeign.checkContact(id);
    }
    @GetMapping("/all")
    public ResponseEntity<Response> getAllContact(){
        return contactFeign.getAllContact();
    }
    @GetMapping("/get/contact")
    public ResponseEntity<Response> getContactByCustom(
            @RequestParam(defaultValue = "10", required = false) String limit,
            @RequestParam(defaultValue = "0", required = false) String page,
            @RequestParam(defaultValue = "", required = false) String q,
            @RequestParam(defaultValue = "asc", required = false) String asc,
            @RequestParam(defaultValue = "none", required = false) String status,
            @RequestParam(defaultValue = "name", required = false) String orderBy
    ){
        return contactFeign.getContactByCustom(limit, page, q, asc, status, orderBy);
    }
}
