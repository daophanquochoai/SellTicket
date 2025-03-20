package doctorhoai.learn.proxy_client.business.film.controller;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.film.model.request.SubRequest;
import doctorhoai.learn.proxy_client.business.film.service.SubFeign;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/film-service/api/sub")
public class SubController {

    private final SubFeign subFeign;

    @GetMapping("/all")
    public ResponseEntity<Response> getSubs(){
        return subFeign.getSubs();
    }
    @GetMapping("/custom")
    public ResponseEntity<Response> getSubs(
            @RequestParam(defaultValue = "0", required = false) String page,
            @RequestParam(defaultValue = "10", required = false) String limit,
            @RequestParam(defaultValue = "", required = false) String q,
            @RequestParam(defaultValue = "asc", required = false) String asc,
            @RequestParam(defaultValue = "name", required = false) String orderBy
    ){
        return subFeign.getSubs(page, limit, q, asc, orderBy);
    }
    @PostMapping("/add")
    public ResponseEntity<Response> addSub(@RequestBody @Valid SubRequest sub){
        return subFeign.addSub(sub);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateSub(
            @PathVariable String id,
            @RequestBody SubRequest sub
    ){
        return subFeign.updateSub(id, sub);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteSub(
            @PathVariable String id
    ){
        return subFeign.deleteSub(id);
    }
}
