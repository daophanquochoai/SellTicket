package doctorhoai.learn.rateservice.repository;

import doctorhoai.learn.rateservice.entity.RateFilm;
import doctorhoai.learn.rateservice.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<RateFilm, String> {
    @Query("select r from RateFilm r where r.filmId = :filmId and r.active = :active and (r.content like concat('%',:q,'%') or r.filmId like concat('%',:q,'%') or r.customerId like concat('%',:q,'%'))")
    Page<RateFilm> getRateFilmByCustom(Pageable pageable, String q, Status active, String filmId);
    @Query("select r from RateFilm r where r.filmId = :filmId and (r.content like concat('%',:q,'%') or r.filmId like concat('%',:q,'%') or r.customerId like concat('%',:q,'%'))")
    Page<RateFilm> getRateFilmByCustom(Pageable pageable, String q, String filmId);
    @Query("select r from RateFilm r where r.active = :active and (r.content like concat('%',:q,'%') or r.filmId like concat('%',:q,'%') or r.customerId like concat('%',:q,'%'))")
    Page<RateFilm> getRateFilmByCustom(Pageable pageable, String q, Status active);
    @Query("select r from RateFilm r where (r.content like concat('%',:q,'%') or r.filmId like concat('%',:q,'%') or r.customerId like concat('%',:q,'%'))")
    Page<RateFilm> getRateFilmByCustom(Pageable pageable, String q);
    @Query("select avg(r.star) from RateFilm r where r.filmId = :filmId")
    Integer getRate(String filmId);
    Optional<RateFilm> getRateByFilmIdAndCustomerId(String filmId, String customerId);
}
