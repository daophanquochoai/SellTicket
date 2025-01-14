package doctorhoai.learn.rateservice.feignclient;

import doctorhoai.learn.rateservice.dto.response.Response;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "userservice", fallback = UserFeignCallBack.class)
public interface UserFeignClient {
    @GetMapping("customer/{id}")
    public ResponseEntity<Response> getCustomerById(
            @PathVariable @NotBlank String id
    );
}
