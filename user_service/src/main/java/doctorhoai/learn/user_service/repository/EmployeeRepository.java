package doctorhoai.learn.user_service.repository;

import doctorhoai.learn.user_service.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    @Procedure(name = "GetActiveEmployee")
    List<Employee> GetActiveEmployee();
}
