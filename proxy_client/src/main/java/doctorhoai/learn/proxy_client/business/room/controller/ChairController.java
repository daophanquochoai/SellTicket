package doctorhoai.learn.proxy_client.business.room.controller;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.room.model.ChairDto;
import doctorhoai.learn.proxy_client.business.room.service.ChairFeign;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chair")
public class ChairController {

    private final ChairFeign chairFeign;

    @GetMapping("/{id}")
    public ResponseEntity<Response> getChairById(@PathVariable @NotBlank String id){
        return chairFeign.getChairById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Response> addChair(@RequestBody @Valid ChairDto chairDto){
        return chairFeign.addChair(chairDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateChair(@PathVariable @NotBlank String id, @RequestBody @Valid ChairDto chairDto){
        return chairFeign.updateChair(id, chairDto);
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllChair(){
        return chairFeign.getAllChair();
    }

    @PatchMapping("/delete{id}")
    public ResponseEntity<Response> deleteChair(@PathVariable @NotBlank String id){
        return chairFeign.deleteChair(id);
    }

    @PatchMapping("/active/{id}")
    public ResponseEntity<Response> activateChair(@PathVariable @NotBlank String id){
        return chairFeign.activateChair(id);
    }
}
