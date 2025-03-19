package doctorhoai.learn.paymentservice.repository;

import doctorhoai.learn.paymentservice.entity.MonthlyTotalPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyTotalPriceRepository extends JpaRepository<MonthlyTotalPrice, Integer> {
    @Procedure(name = "getMonthlyTotalPrice")
    List<MonthlyTotalPrice> getMonthlyTotalPrice(@Param("yearParam") Integer year);
    @Procedure(name = "getYearForBill")
    List<String> getYearForBill();
}
