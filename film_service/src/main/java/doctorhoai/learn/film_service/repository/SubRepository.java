package doctorhoai.learn.film_service.repository;

import doctorhoai.learn.film_service.entity.Sub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubRepository extends JpaRepository<Sub, String> {
}
