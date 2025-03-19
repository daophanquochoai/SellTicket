package doctorhoai.learn.dishservice.repository;

import doctorhoai.learn.dishservice.entity.Status;
import doctorhoai.learn.dishservice.entity.TypeDish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeDishRepository extends JpaRepository<TypeDish, String> {
    @Query("select f from TypeDish f join fetch f.dish where f.active = 'ACTIVE'")
    List<TypeDish> findAllFetch();
    @Query("select td from TypeDish td where td.active = :status and td.name like concat('%',:q,'%')")
    Page<TypeDish> getTypeDishByCustom(Pageable pageable, String q, Status status);
    @Query("select td from TypeDish td where td.name like concat('%',:q,'%')")
    Page<TypeDish> getTypeDishByCustom(Pageable pageable, String q);
}
