package doctorhoai.learn.paymentservice.service.inter;

import doctorhoai.learn.paymentservice.dto.BillDto;

import java.util.List;

public interface BillService {
    BillDto createBill(BillDto billDto);
    List<BillDto> getAllBills();
    BillDto getBillById(String id);
    void deleteBill(String id);
    void activeBill(String id);
}
