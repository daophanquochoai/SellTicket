package doctorhoai.learn.proxy_client.business.film.controller;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.film.service.SubFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/film-service/api/sub")
public class SubController {

    private final SubFeign subFeign;

    @GetMapping("/all")
    public ResponseEntity<Response> getSubs(){
        return subFeign.getSubs();
    }
}
