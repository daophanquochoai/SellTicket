package doctorhoai.learn.proxy_client.business.user.service.fallback;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.user.model.request.CustomerRequest;
import doctorhoai.learn.proxy_client.business.user.service.CustomerFeign;
import doctorhoai.learn.proxy_client.security.FunctionCommon;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerFeignCallBack implements FallbackFactory<CustomerFeign> {

    private final FunctionCommon functionCommon;

    @Override
    public CustomerFeign create(Throwable cause) {
        return new CustomerFeign() {
            @Override
            public ResponseEntity<Response> getAllCustomer() {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> deleteCustomer(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> activeCustomer(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> getCustomerById(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> addCustomer(CustomerRequest customerRequest) {
                return functionCommon.process(cause);
            }
        };
    }

}
