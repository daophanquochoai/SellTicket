package doctorhoai.learn.paymentservice.facade;

import doctorhoai.learn.paymentservice.dto.response.Response;
import doctorhoai.learn.paymentservice.service.feign.FilmFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Component
@RequiredArgsConstructor
public class FilmAsync {

    private final Executor executor;
    private final FilmFeign filmFeign;

    public CompletableFuture<ResponseEntity<Response>> getFilmById(String id) {
        System.out.println("Thread: " + Thread.currentThread().getName());
        return CompletableFuture.supplyAsync(() -> filmFeign.getFilmById(id),executor);
    }
}
