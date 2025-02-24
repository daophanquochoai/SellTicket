package doctorhoai.learn.user_service.repository;

import doctorhoai.learn.user_service.entity.AccountBanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountBankRepository extends JpaRepository<AccountBanking, String> {
    List<AccountBanking> findByCustomerId_Id(String customerId);
}
