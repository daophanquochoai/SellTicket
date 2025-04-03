package doctorhoai.learn.paymentservice.facade;

import doctorhoai.learn.paymentservice.dto.response.Response;
import doctorhoai.learn.paymentservice.service.feign.FilmShowTimeFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Component
@RequiredArgsConstructor
public class FilmShowAsync {

    private final Executor executor;
    private final FilmShowTimeFeign feign;

    public CompletableFuture<ResponseEntity<Response>> getFilmShowByRoomAndId(String roomId, Integer id){
        System.out.println("Thread: " + Thread.currentThread().getName());
        return CompletableFuture.supplyAsync(() -> feign.getFilmShowByRoomAndId(roomId, id),executor);
    }
    public CompletableFuture<ResponseEntity<Response>> getFilmShowTime(Integer id){
        System.out.println("Thread: " + Thread.currentThread().getName());
        return CompletableFuture.supplyAsync(()-> feign.getFilmShowTime(id),executor);
    }
}
