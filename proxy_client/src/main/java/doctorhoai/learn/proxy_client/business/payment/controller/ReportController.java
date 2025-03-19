package doctorhoai.learn.proxy_client.business.payment.controller;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.payment.service.ReportFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment-service/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportFeign reportFeign;

    @GetMapping("/year/{year}")
    public ResponseEntity<Response> getReportByYear(@PathVariable Integer year){
        return reportFeign.getReportByYear(year);
    }
    @GetMapping("/year")
    public ResponseEntity<Response> getReport() {
        return reportFeign.getReport();
    }
}
