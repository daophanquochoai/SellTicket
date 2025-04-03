package doctorhoai.learn.showtimeservice.facade;

import doctorhoai.learn.showtimeservice.dto.response.Response;
import doctorhoai.learn.showtimeservice.service.client.feign.FilmFeign;
import doctorhoai.learn.showtimeservice.service.client.feign.RoomFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class RoomAsync {

    private final RoomFeign roomFeign;

    public CompletableFuture<ResponseEntity<Response>> getRoomById(String id){
        return CompletableFuture.supplyAsync(() -> roomFeign.getRoomById(id));
    }
    public CompletableFuture<ResponseEntity<Response>> getRoomByBranch(String branch){
        return CompletableFuture.supplyAsync(() -> roomFeign.getRoomByBranch(branch));
    }

}
