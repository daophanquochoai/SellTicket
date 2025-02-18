package doctorhoai.learn.showtimeservice.repository;

import doctorhoai.learn.showtimeservice.entity.FilmShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FilmShowRepository extends JpaRepository<FilmShowTime, Integer> {
    List<FilmShowTime> getFilmShowTimeByRoomIdAndTimestamp(String roomId, LocalDate timestamp);
}
