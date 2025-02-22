package doctorhoai.learn.paymentservice.repository;

import doctorhoai.learn.paymentservice.entity.BillDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillDishRepository extends JpaRepository<BillDish, String> {
    List<BillDish> getBillDishByBillDishId_Id(String id);
}
