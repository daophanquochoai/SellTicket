package doctorhoai.learn.paymentservice.service.inter;

import doctorhoai.learn.paymentservice.entity.MonthlyTotalPrice;

import java.util.List;

public interface ReportService {
    List<MonthlyTotalPrice> getReport(Integer year);
    List<String> getYearForBill();
}
