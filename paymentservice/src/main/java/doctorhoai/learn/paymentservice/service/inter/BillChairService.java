package doctorhoai.learn.paymentservice.service.inter;

import doctorhoai.learn.paymentservice.dto.BillChairDto;

import java.util.List;

public interface BillChairService {
    List<BillChairDto> getBillChairByTimeAndFilmShowId(Integer filmShowId);
}
