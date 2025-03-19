package doctorhoai.learn.paymentservice.service.inter;

import doctorhoai.learn.paymentservice.dto.BillDto;
import doctorhoai.learn.paymentservice.dto.PageObject;

public interface BillService {
    BillDto createBill(BillDto billDto);
    PageObject getAllBills(String page, String limit, String active, String orderBy, String asc, String q);
    BillDto getBillById(String id);
    void deleteBill(String id);
    void activeBill(String id);
    boolean acceptBill(String id, String transaction);
}
