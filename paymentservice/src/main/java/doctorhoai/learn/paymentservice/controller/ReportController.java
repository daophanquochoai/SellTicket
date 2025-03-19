package doctorhoai.learn.paymentservice.controller;

import doctorhoai.learn.paymentservice.dto.response.Response;
import doctorhoai.learn.paymentservice.service.inter.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/year/{year}")
    public ResponseEntity<Response> getReportByYear(@PathVariable Integer year) {
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(200)
                        .message("Get report successfully")
                        .data(reportService.getReport(year))
                        .build()
        );
    }
    @GetMapping("/year")
    public ResponseEntity<Response> getReport() {
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(200)
                        .message("Get report successfully")
                        .data(reportService.getYearForBill())
                        .build()
        );
    }
}
