package doctorhoai.learn.paymentservice.service.impl;

import doctorhoai.learn.paymentservice.dto.BillChairDto;
import doctorhoai.learn.paymentservice.entity.BillChair;
import doctorhoai.learn.paymentservice.entity.Status;
import doctorhoai.learn.paymentservice.helper.MapperToObject;
import doctorhoai.learn.paymentservice.repository.BillChairRepository;
import doctorhoai.learn.paymentservice.service.inter.BillChairService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillChairServiceImpl implements BillChairService {

    private final BillChairRepository billChairRepository;
    private final MapperToObject mapperToObject;

    @Override
    public List<BillChairDto> getBillChairByTimeAndFilmShowId( Integer filmShowId) {
        List<BillChair> list = billChairRepository.getBillChairByBillChairId_FilmShowTimeIdAndBillChairId_Status(filmShowId, Status.SUCCESS);
        return list.stream().map(mapperToObject::mapperToBillChairDto).toList();
    }
}
