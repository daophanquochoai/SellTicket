package doctorhoai.learn.user_service.repository;

import doctorhoai.learn.user_service.entity.Employee;
import doctorhoai.learn.user_service.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    @Procedure(name = "GetActiveEmployee")
    List<Employee> GetActiveEmployee();
    Optional<Employee> findByEmail(String email);
    @Query("select e from Employee e where e.status = :status and (e.name like concat('%',:q,'%') or e.email like concat('%',:q,'%') or e.CCCD like concat('%',:q,'%'))")
    Page<Employee> getAllByCustom(Pageable pageable, String q, Status status);
    @Query("select e from Employee e where e.name like concat('%',:q,'%') or e.email like concat('%',:q,'%') or e.CCCD like concat('%',:q,'%')")
    Page<Employee> getAllByCustom(Pageable pageable, String q);
}
