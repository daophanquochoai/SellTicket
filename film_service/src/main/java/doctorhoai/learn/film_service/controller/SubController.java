package doctorhoai.learn.film_service.controller;

import doctorhoai.learn.film_service.dto.response.Response;
import doctorhoai.learn.film_service.service.inter.SubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sub")
@RequiredArgsConstructor
public class SubController {

    private final SubService subService;

    @GetMapping("/all")
    public ResponseEntity<Response> getSubs(){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(200)
                        .message("Get Sub Successfully")
                        .data(subService.getSubs())
                        .build()
        );
    }
}
