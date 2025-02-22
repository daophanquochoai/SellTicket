package doctorhoai.learn.paymentservice.repository;

import doctorhoai.learn.paymentservice.entity.BillChair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillChairRepository extends JpaRepository<BillChair, String> {
    Optional<BillChair> getBillChairByChairCode(String chairCode);
    List<BillChair> getBillChairByBillChairId_Id(String id);
}
