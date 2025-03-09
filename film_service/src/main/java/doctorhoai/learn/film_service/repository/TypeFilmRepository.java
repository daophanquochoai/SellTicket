package doctorhoai.learn.film_service.repository;

import doctorhoai.learn.film_service.entity.Status;
import doctorhoai.learn.film_service.entity.TypeFilm;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeFilmRepository extends JpaRepository<TypeFilm, String> {
    @Query("select t from TypeFilm t where (t.name like concat('%',:q, '%') )")
    List<TypeFilm> getTypeFilmByCustom(Pageable pageable, String q);
    @Query("select t from TypeFilm t where t.active = :status and (t.name like concat('%',:q, '%') )")
    List<TypeFilm> getTypeFilmByCustom(Pageable pageable, String q, Status status);
}
