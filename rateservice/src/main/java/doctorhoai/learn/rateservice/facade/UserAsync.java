package doctorhoai.learn.rateservice.facade;

import doctorhoai.learn.rateservice.dto.response.Response;
import doctorhoai.learn.rateservice.feignclient.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class UserAsync {

    private final UserFeignClient userFeignClient;

    public CompletableFuture<ResponseEntity<Response>> getCustomerById(String id) {
        return CompletableFuture.supplyAsync(()->userFeignClient.getCustomerById(id));
    }

}
