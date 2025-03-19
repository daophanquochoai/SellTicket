package doctorhoai.learn.proxy_client.business.user.service;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.user.service.fallback.AccountBankFeignFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "userservice", contextId = "accountProxyClient", path = "/account", fallbackFactory = AccountBankFeignFallBack.class)
public interface AccountFeign {

    @GetMapping("/num")
    public ResponseEntity<Response> getNumCustomerAndEmployee();
}
