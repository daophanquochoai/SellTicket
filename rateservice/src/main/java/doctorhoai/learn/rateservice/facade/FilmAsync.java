package doctorhoai.learn.rateservice.facade;

import doctorhoai.learn.rateservice.dto.response.Response;
import doctorhoai.learn.rateservice.feignclient.FilmFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class FilmAsync {

    private final FilmFeignClient filmClient;
    public CompletableFuture<ResponseEntity<Response>> getFilmId(String id){
        return CompletableFuture.supplyAsync(() -> filmClient.getFilmById(id));
    }
}
