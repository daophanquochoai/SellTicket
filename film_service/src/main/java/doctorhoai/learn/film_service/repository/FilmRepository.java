package doctorhoai.learn.film_service.repository;

import doctorhoai.learn.film_service.entity.Film;
import doctorhoai.learn.film_service.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<Film, String> {
    @Query("select f from Film f where f.status = :status and (lower(f.content) like concat('%', lower(:q), '%') or f.name like concat('%', lower(:q), '%') or lower(f.description) like concat('%',lower(:q), '%'))")
    Page<Film> getFilmByCustom(Pageable pageable, String q, Status status);
    @Query("select f from Film f where lower(f.content) like concat('%', lower(:q), '%') or lower(f.name) like concat('%', lower(:q), '%') or lower(f.description) like concat('%',lower(:q), '%')")
    Page<Film> getFilmByCustom(Pageable pageable, String q);
    @Query("select f from Film f join fetch SubFilm sl on f.id = :s")
    Optional<Film> findFilmBySub(String s);
    @Query("SELECT f FROM Film f JOIN f.subFilms sl WHERE sl.id IN :ids AND f.status = 'ACTIVE'")
    List<Film> getFilmBySubFilmsIdIn(List<String> ids);
    List<Film> getFilmByStatus(Status status);
    @Query("select f from Film f where f.status != 'DELETE'")
    List<Film> getFilmByOrther();
    @Query("select f from Film f where f.id not in (select s.filmId.id from SubFilm s where s.subId.id = :subId)")
    List<Film> getFilmByNotInSub(String subId);

}
