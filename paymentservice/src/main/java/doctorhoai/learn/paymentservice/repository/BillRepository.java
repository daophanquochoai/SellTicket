package doctorhoai.learn.paymentservice.repository;

import doctorhoai.learn.paymentservice.entity.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface BillRepository extends JpaRepository<Bill, String> {
    @Query("select b from Bill b where ( b.userName like concat('%', :q, '%') or b.email like concat('%', :q, '%') or b.numberPhone like concat('%', :q, '%') or b.transactionCode like concat('%', :q, '%') ) and b.active = :active")
    Page<Bill> findAllCustom(Pageable pageable, String q, String active);
    @Query("select b from Bill b where b.userName like concat('%', :q, '%') or b.email like concat('%', :q, '%') or b.numberPhone like concat('%', :q, '%') or b.transactionCode like concat('%', :q, '%') ")
    Page<Bill> findAllCustom(Pageable pageable, String q);
}
