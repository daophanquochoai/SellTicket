package doctorhoai.learn.proxy_client.business.user.service.fallback;

import doctorhoai.learn.proxy_client.business.user.model.response.Response;
import doctorhoai.learn.proxy_client.business.user.model.request.EmployeeRequest;
import doctorhoai.learn.proxy_client.business.user.service.EmploymentFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeFeignCallBack implements FallbackFactory<EmploymentFeign> {

    private final FunctionCommon functionCommon;
    @Override
    public EmploymentFeign create(Throwable cause) {
        return new EmploymentFeign() {
            @Override
            public ResponseEntity<Response> getAllEmployees() {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> updateEmployee(String id, EmployeeRequest employee) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> addEmployee(EmployeeRequest employee) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> hiddenEmployee(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> activeEmployee(String id) {
                return functionCommon.process(cause);
            }
        };
    }
}
