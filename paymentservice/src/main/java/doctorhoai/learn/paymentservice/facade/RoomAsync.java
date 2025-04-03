package doctorhoai.learn.paymentservice.facade;

import doctorhoai.learn.paymentservice.dto.response.Response;
import doctorhoai.learn.paymentservice.service.feign.RoomFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Component
@RequiredArgsConstructor
public class RoomAsync {

    private final Executor executor;
    private final RoomFeign roomFeign;

    public CompletableFuture<ResponseEntity<Response>> getRoomById( String id){
        System.out.println("Thread: " + Thread.currentThread().getName());
        return CompletableFuture.supplyAsync(() -> roomFeign.getRoomById(id),executor);
    }
}
