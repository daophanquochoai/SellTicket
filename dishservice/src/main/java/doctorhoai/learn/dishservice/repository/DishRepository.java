package doctorhoai.learn.dishservice.repository;

import doctorhoai.learn.dishservice.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, String> {
    Optional<Dish> findByName(String name);
}
