package doctorhoai.learn.film_service.repository;

import doctorhoai.learn.film_service.entity.SubFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SubFilmRepository extends JpaRepository<SubFilm, String> {
    Optional<SubFilm> getSubFilmsByFilmId_IdAndSubId_Id(String film_id, String sub_id);
    @Modifying
    @Transactional
    @Query("DELETE FROM SubFilm sf WHERE sf.filmId IS NULL")
    void deleteBySubFilm();
}
