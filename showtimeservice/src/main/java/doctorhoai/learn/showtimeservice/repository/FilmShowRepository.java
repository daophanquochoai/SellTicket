package doctorhoai.learn.showtimeservice.repository;

import doctorhoai.learn.showtimeservice.entity.FilmShowTime;
import doctorhoai.learn.showtimeservice.entity.Status;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FilmShowRepository extends JpaRepository<FilmShowTime, Integer> {
    List<FilmShowTime> getFilmShowTimeByRoomIdAndTimestampAndStatus(String roomId, LocalDate timestamp, Status status);
    Optional<FilmShowTime> getFilmShowTimeByRoomIdAndIdAndStatus(@NotNull String roomId, Integer filmId, Status status );
    List<FilmShowTime> getShowTimeByTimestampAndSubFilmIdAndStatus( LocalDate time, String subFilmId, Status status );
    List<FilmShowTime> getFilmShowTimeBySubFilmId(String SubFilmIs);
}
