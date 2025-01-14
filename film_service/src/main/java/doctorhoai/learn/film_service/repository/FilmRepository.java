package doctorhoai.learn.film_service.repository;

import doctorhoai.learn.film_service.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<Film, String> {
}
