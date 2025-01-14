package doctorhoai.learn.rateservice.feignclient;

import doctorhoai.learn.rateservice.dto.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserFeignCallBack implements UserFeignClient{
    @Override
    public ResponseEntity<Response> getCustomerById(String id) {
        return null;
    }
}
