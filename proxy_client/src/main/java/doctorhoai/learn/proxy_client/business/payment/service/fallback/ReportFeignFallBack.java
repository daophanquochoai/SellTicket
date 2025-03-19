package doctorhoai.learn.proxy_client.business.payment.service.fallback;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.payment.service.ReportFeign;
import doctorhoai.learn.proxy_client.security.FunctionCommon;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportFeignFallBack implements FallbackFactory<ReportFeign> {

    private final FunctionCommon functionCommon;

    @Override
    public ReportFeign create(Throwable cause) {
        return new ReportFeign() {
            @Override
            public ResponseEntity<Response> getReportByYear(Integer year) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> getReport() {
                return functionCommon.process(cause);
            }
        };
    }
}
