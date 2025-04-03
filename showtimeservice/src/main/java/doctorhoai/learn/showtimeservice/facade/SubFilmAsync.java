package doctorhoai.learn.showtimeservice.facade;

import doctorhoai.learn.showtimeservice.dto.response.Response;
import doctorhoai.learn.showtimeservice.service.client.feign.SubFilmFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class SubFilmAsync {

    private final SubFilmFeign subFilmFeign;

    public CompletableFuture<ResponseEntity<Response>> getSubFilmById(String id) {
        return CompletableFuture.supplyAsync(()-> subFilmFeign.getSubFilmById(id));
    }

    public CompletableFuture<ResponseEntity<Response>> getSubFilmByFilmIdAndSubId(String filmId, String subId) {
        return CompletableFuture.supplyAsync(()->subFilmFeign.getSubFilmByFilmIdAndSubId(filmId, subId));
    }
}
