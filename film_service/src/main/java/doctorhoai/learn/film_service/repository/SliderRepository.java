package doctorhoai.learn.film_service.repository;

import doctorhoai.learn.film_service.entity.Slider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SliderRepository extends JpaRepository<Slider, Integer> {
}
