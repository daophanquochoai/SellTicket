package doctorhoai.learn.film_service.controller;

import doctorhoai.learn.film_service.dto.SliderDto;
import doctorhoai.learn.film_service.dto.response.Response;
import doctorhoai.learn.film_service.service.inter.SliderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/slider")
@Tag(name = "Slider Controller", description = "Handler slider operation")
public class SliderController {

    private final SliderService sliderService;

    @PostMapping("/add")
    public ResponseEntity<Response> uploadImages(
            @RequestBody @Valid List<SliderDto> list
            )
    {
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(201)
                        .message("Add slider in database")
                        .data(sliderService.changeSlider(list))
                        .build()
        );
    }

    @PostMapping("/add/slider")
    public ResponseEntity<Response> uploadImage(
            @RequestBody @Valid SliderDto list
    )
    {
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(201)
                        .message("Add slider in database")
                        .data(sliderService.saveSlider(list))
                        .build()
        );
    }

    @GetMapping("/get")
    public ResponseEntity<Response> getSlider(){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(200)
                        .message("Get slider in database")
                        .data(sliderService.getAll())
                        .build()
        );
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Response> removeSlider(
            @PathVariable Integer id
    ){
        sliderService.removeSlider(id);
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(200)
                        .message("Remove slider in database")
                        .build()
        );
    }
}
