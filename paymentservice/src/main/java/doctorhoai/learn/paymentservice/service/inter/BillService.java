package doctorhoai.learn.paymentservice.service.inter;

import doctorhoai.learn.paymentservice.dto.BillDto;
import doctorhoai.learn.paymentservice.dto.PageObject;

import java.util.List;

public interface BillService {
    BillDto createBill(BillDto billDto);
    PageObject getAllBills(String page, String limit, String active, String orderBy, String asc, String q);
    BillDto getBillById(String id);
    void deleteBill(String id);
    void activeBill(String id);
    BillDto acceptBill(String id, String transaction);
    List<BillDto> getAllBillByFilmShow(Integer filmShow);
}
