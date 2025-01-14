package doctorhoai.learn.film_service.repository;

import doctorhoai.learn.film_service.entity.TypeFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeFilmRepository extends JpaRepository<TypeFilm, String> {
}
