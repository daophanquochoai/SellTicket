package doctorhoai.learn.paymentservice.repository;

import doctorhoai.learn.paymentservice.entity.Active;
import doctorhoai.learn.paymentservice.entity.BillChair;
import doctorhoai.learn.paymentservice.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillChairRepository extends JpaRepository<BillChair, String> {
    Optional<BillChair> getBillChairByChairCodeAndBillChairId_FilmShowTimeIdAndBillChairId_StatusAndBillChairId_Active(String chairCode, Integer filmShowId, Status status, Active active);
    List<BillChair> getBillChairByBillChairId_Id(String id);
    List<BillChair> getBillChairByBillChairId_FilmShowTimeIdAndBillChairId_Status(Integer filmShowTimeId, Status status);
}
