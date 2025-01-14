package doctorhoai.learn.rateservice.repository;

import doctorhoai.learn.rateservice.entity.RateFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<RateFilm, String> {
    List<RateFilm> findRateFilmByFilmId(String filmId);
}
