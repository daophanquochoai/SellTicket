package doctorhoai.learn.proxy_client.business.user.service.fallback;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.user.service.AccountFeign;
import doctorhoai.learn.proxy_client.security.FunctionCommon;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountFeignFallBack implements FallbackFactory<AccountFeign> {

    private final FunctionCommon functionCommon;

    @Override
    public AccountFeign create(Throwable cause) {
        return new AccountFeign() {
            @Override
            public ResponseEntity<Response> getNumCustomerAndEmployee() {
                return functionCommon.process(cause);
            }
        };
    }
}
