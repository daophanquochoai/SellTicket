package doctorhoai.learn.proxy_client.business.film.controller;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.film.model.SliderDto;
import doctorhoai.learn.proxy_client.business.film.service.SliderFeign;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/film-service/api/slider")
public class SliderController {

    private final SliderFeign sliderFeign;

    @PostMapping("/add")
    public ResponseEntity<Response> uploadImages(
            @RequestBody @Valid List<SliderDto> list
    )
    {
        return sliderFeign.uploadImages(list);
    }
    @PostMapping("/add/slider")
    public ResponseEntity<Response> uploadImage(
            @RequestBody @Valid SliderDto list
    )
    {
        return sliderFeign.uploadImage(list);
    }
    @GetMapping("/get")
    public ResponseEntity<Response> getSlider(){
        return sliderFeign.getSlider();
    }
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Response> removeSlider(
            @PathVariable Integer id
    ){
        return sliderFeign.removeSlider(id);
    }
}
