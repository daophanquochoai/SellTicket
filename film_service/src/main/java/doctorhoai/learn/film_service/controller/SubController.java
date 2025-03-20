package doctorhoai.learn.film_service.controller;

import doctorhoai.learn.film_service.dto.SubDto;
import doctorhoai.learn.film_service.dto.request.SubRequest;
import doctorhoai.learn.film_service.dto.response.Response;

import doctorhoai.learn.film_service.service.inter.SubService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/custom")
    public ResponseEntity<Response> getSubs(
            @RequestParam(defaultValue = "0", required = false) String page,
            @RequestParam(defaultValue = "10", required = false) String limit,
            @RequestParam(defaultValue = "", required = false) String q,
            @RequestParam(defaultValue = "asc", required = false) String asc,
            @RequestParam(defaultValue = "name", required = false) String orderBy
    ){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(200)
                        .message("Get Sub Successfully")
                        .data(subService.getSubsByCustom(page,limit,asc,orderBy,q))
                        .build()
        );
    }
    @PostMapping("/add")
    public ResponseEntity<Response> addSub(@RequestBody @Valid SubRequest sub){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Response.builder()
                        .statusCode(201)
                        .message("Add Sub Successfully")
                        .data(subService.addSub(sub))
                        .build()
        );
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateSub(
            @PathVariable String id,
            @RequestBody SubRequest sub
    ){
        return ResponseEntity.status(HttpStatus.OK).body(
                Response.builder()
                        .statusCode(200)
                        .message("Update Sub Successfully")
                        .data(subService.updateSub(id, sub))
                        .build()
        );
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteSub(
            @PathVariable String id
    ){
        Boolean check = subService.deleteSub(id);
        if( check ){
            return ResponseEntity.status(HttpStatus.OK).body(
                    Response.builder()
                            .statusCode(200)
                            .message("Delete Sub Successfully")
                            .build()
            );
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Response.builder()
                            .statusCode(400)
                            .message("Delete Sub Fail")
                            .build()
            );
        }
    }
}
