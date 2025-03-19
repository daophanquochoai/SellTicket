package doctorhoai.learn.proxy_client.business.payment.service;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.payment.service.fallback.ReportFeignFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "paymentservice",
        contextId = "reportProxyService",
        path = "/report",
        fallbackFactory = ReportFeignFallBack.class
)
public interface ReportFeign {

    @GetMapping("/year/{year}")
    public ResponseEntity<Response> getReportByYear(@PathVariable Integer year);
    @GetMapping("/year")
    public ResponseEntity<Response> getReport();
}
