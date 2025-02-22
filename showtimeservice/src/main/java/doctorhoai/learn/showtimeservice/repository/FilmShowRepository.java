package doctorhoai.learn.showtimeservice.repository;

import doctorhoai.learn.showtimeservice.entity.FilmShowTime;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FilmShowRepository extends JpaRepository<FilmShowTime, Integer> {
    List<FilmShowTime> getFilmShowTimeByRoomIdAndTimestamp(String roomId, LocalDate timestamp);
    Optional<FilmShowTime> getFilmShowTimeByRoomIdAndId(@NotNull String roomId, Integer filmId);
}
