package doctorhoai.learn.roomservice.repository;

import doctorhoai.learn.roomservice.entity.Chair;
import doctorhoai.learn.roomservice.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChairRepository extends JpaRepository<Chair, String> {
    @Query("select c from Chair c where c.status = :status and (c.name like concat('%',:q,'%'))")
    Page<Chair> getChairByCustom(Pageable pageable, String q, Status status);
    @Query("select c from Chair c where (c.name like concat('%',:q,'%'))")
    Page<Chair> getChairByCustom(Pageable pageable, String q);
}
