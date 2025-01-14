package doctorhoai.learn.rateservice.feignclient;

import doctorhoai.learn.rateservice.dto.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class FilmFeignCallBack implements FilmFeignClient{
    @Override
    public ResponseEntity<Response> getFilmById(String id) {
        return null;
    }
}
