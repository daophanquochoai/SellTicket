package doctorhoai.learn.showtimeservice.repository;

import doctorhoai.learn.showtimeservice.entity.FilmShowTime;
import doctorhoai.learn.showtimeservice.entity.Status;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FilmShowRepository extends JpaRepository<FilmShowTime, Integer> {
    List<FilmShowTime> getFilmShowTimeByRoomIdAndTimestampAndStatus(String roomId, LocalDate timestamp, Status status);
    Optional<FilmShowTime> getFilmShowTimeByRoomIdAndIdAndStatus(@NotNull String roomId, Integer filmId, Status status );
    List<FilmShowTime> getShowTimeByTimestampAndSubFilmIdAndStatus( LocalDate time, String subFilmId, Status status );
    List<FilmShowTime> getFilmShowTimeBySubFilmId(String SubFilmIs);
    @Query("""
        select s from FilmShowTime s where s.timestamp = :timestamp and ( s.timeStart = :start or s.timeEnd = :end or (s.timeStart <= :start and s.timeEnd >= :start) or (s.timeStart <= :end and s.timeEnd >= :end)) and s.roomId = :roomId and s.status = :status
    """)
    List<FilmShowTime> checkFilmShowTime(Time start, Time end, LocalDate timestamp, String roomId, Status status);
    @Query("""
        select f from FilmShowTime f where f.roomId = :roomId and f.status = :status and f.timestamp >= :timestamp
    """)
    List<FilmShowTime> getFilmShowByRoomAndStatus(String roomId, Status status, LocalDate timestamp);
}
