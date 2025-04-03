package doctorhoai.learn.film_service.repository;

import doctorhoai.learn.film_service.entity.RevenueFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RevenueRepository extends JpaRepository<RevenueFilm, String> {
    @Procedure(name = "revenueInFilm")
    List<RevenueFilm> revenueInFilm(Integer monthNow, Integer yearNow);
    @Procedure(name = "revenueInFilmByFilmId")
    List<RevenueFilm> revenueInFilmByFilmId(String filmId);
}
