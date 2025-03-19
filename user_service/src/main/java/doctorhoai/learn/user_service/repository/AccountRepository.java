package doctorhoai.learn.user_service.repository;

import doctorhoai.learn.user_service.dto.response.ReportAccount;
import doctorhoai.learn.user_service.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByUserName(String username);
}
