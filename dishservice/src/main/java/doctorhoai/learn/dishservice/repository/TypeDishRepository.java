package doctorhoai.learn.dishservice.repository;

import doctorhoai.learn.dishservice.entity.TypeDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeDishRepository extends JpaRepository<TypeDish, String> {
}
