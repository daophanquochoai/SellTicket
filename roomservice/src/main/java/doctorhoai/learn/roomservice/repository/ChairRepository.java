package doctorhoai.learn.roomservice.repository;

import doctorhoai.learn.roomservice.entity.Chair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChairRepository extends JpaRepository<Chair, String> {
}
