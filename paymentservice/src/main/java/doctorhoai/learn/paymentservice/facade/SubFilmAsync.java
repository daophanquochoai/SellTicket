package doctorhoai.learn.paymentservice.facade;

import doctorhoai.learn.paymentservice.dto.response.Response;
import doctorhoai.learn.paymentservice.service.feign.SubFilmFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Component
@RequiredArgsConstructor
public class SubFilmAsync {

    private final Executor executor;
    private final SubFilmFeign subFilmFeign;

    public CompletableFuture<ResponseEntity<Response>> getSubFilmById(String id){
        System.out.println("Thread: " + Thread.currentThread().getName());
        return CompletableFuture.supplyAsync(() -> subFilmFeign.getSubFilmById(id),executor);
    }
}
