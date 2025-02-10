package doctorhoai.learn.roomservice.repository;

import doctorhoai.learn.roomservice.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<Branch, String> {
}
