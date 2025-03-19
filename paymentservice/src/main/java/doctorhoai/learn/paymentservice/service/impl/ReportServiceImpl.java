package doctorhoai.learn.paymentservice.service.impl;

import doctorhoai.learn.paymentservice.entity.MonthlyTotalPrice;
import doctorhoai.learn.paymentservice.repository.MonthlyTotalPriceRepository;
import doctorhoai.learn.paymentservice.service.inter.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final MonthlyTotalPriceRepository totalPriceRepository;

    @Transactional( readOnly = false)
    @Override
    public List<MonthlyTotalPrice> getReport( Integer year) {
        return totalPriceRepository.getMonthlyTotalPrice(year);
    }

    @Transactional
    @Override
    public List<String> getYearForBill() {
        return totalPriceRepository.getYearForBill();
    }
}
