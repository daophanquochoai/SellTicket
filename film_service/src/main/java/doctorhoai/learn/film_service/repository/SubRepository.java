package doctorhoai.learn.film_service.repository;

import doctorhoai.learn.film_service.entity.Sub;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubRepository extends JpaRepository<Sub, String> {
    @Query("select s from Sub s where s.sub like concat('%', :q, '%')")
    Page<Sub> getSubByCustom(Pageable pageable, String q);
    @Query("select sl.subId from SubFilm sl where sl.subId.id = :id")
    Optional<Sub> findSubInSubFilm(String id);
}
