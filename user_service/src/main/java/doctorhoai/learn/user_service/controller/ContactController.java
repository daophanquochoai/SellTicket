package doctorhoai.learn.user_service.controller;

import doctorhoai.learn.user_service.dto.ContactDto;
import doctorhoai.learn.user_service.dto.response.Response;
import doctorhoai.learn.user_service.service.inter.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contact")
public class ContactController {

    private final ContactService contactService;

    @PostMapping("/add")
    public ResponseEntity<Response> addContact(
            @RequestBody @Valid ContactDto contactDto
            ){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Response.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Add contact successfully")
                        .data(contactService.addContact(contactDto))
                        .build()
        );
    }
    @PutMapping("/check/{id}")
    public ResponseEntity<Response> checkContact(
            @PathVariable Integer id
    ){
        return ResponseEntity.status(HttpStatus.OK).body(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Check contact successfully")
                        .data(contactService.checkContact(id))
                        .build()
        );
    }
    @GetMapping("/all")
    public ResponseEntity<Response> getAllContact(){
        return ResponseEntity.status(HttpStatus.OK).body(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("All contact successfully")
                        .data(contactService.getAllContacts())
                        .build()
        );
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
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Get customer successfully")
                        .data(contactService.getContactByCustom(page,limit,asc,q,orderBy,status))
                        .build()
        );
    }
}
