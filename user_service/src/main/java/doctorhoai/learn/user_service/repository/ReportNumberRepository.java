package doctorhoai.learn.user_service.repository;

import doctorhoai.learn.user_service.dto.response.ReportAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportNumberRepository extends JpaRepository<ReportAccount, Long> {

    @Procedure(name = "getNumCustomerAndEmployee")
    ReportAccount getNumCustomerAndEmployee();
}
