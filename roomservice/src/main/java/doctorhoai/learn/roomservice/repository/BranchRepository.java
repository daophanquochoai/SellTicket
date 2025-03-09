package doctorhoai.learn.roomservice.repository;

import doctorhoai.learn.roomservice.entity.Branch;
import doctorhoai.learn.roomservice.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, String> {
    @Query("select b from Branch b where b.status = :status and (b.nameBranch like concat('%',:q,'%') or b.address like concat('%',:q,'%') )")
    Page<Branch> getBranchByCustom(Pageable pageable, String q, Status status);
    @Query("select b from Branch b where (b.nameBranch like concat('%',:q,'%') or b.address like concat('%',:q,'%') )")
    Page<Branch> getBranchByCustom(Pageable pageable, String q);
    List<Branch> getBranchByStatus(Status status);
}
