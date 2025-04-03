package doctorhoai.learn.paymentservice.facade;

import doctorhoai.learn.paymentservice.dto.response.Response;
import doctorhoai.learn.paymentservice.service.feign.DishFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Component
@RequiredArgsConstructor
public class DishAsync {

    private final DishFeign dishFeign;
    private final Executor executor;

    public CompletableFuture<ResponseEntity<Response>> getDishById(String id){
        System.out.println("Thread: " + Thread.currentThread().getName());
        return CompletableFuture.supplyAsync(() -> dishFeign.getDishById(id),executor);
    }
    public CompletableFuture<ResponseEntity<Response>> getAllDish(){
        System.out.println("Thread: " + Thread.currentThread().getName());
        return CompletableFuture.supplyAsync(() -> dishFeign.getAllDish(),executor);
    }
}
