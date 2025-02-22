package doctorhoai.learn.showtimeservice.service;

import doctorhoai.learn.showtimeservice.dto.FilmShowDto;
import doctorhoai.learn.showtimeservice.dto.request.FilmShowRequest;

import java.time.LocalDate;
import java.util.List;

public interface FilmShowService {
    FilmShowDto addFilmShow(FilmShowRequest filmShowRequest);
    FilmShowDto updateFilmShow(Integer id, FilmShowRequest filmShowRequest);
    void deleteFilmShow(Integer id);
    void activeFilmShow(Integer id);
    List<FilmShowDto> getFilmShows(String roomId, LocalDate date);
    public FilmShowDto getFilmShowByRoomIdAndFilmShowDto(String roomId, Integer Id);
    FilmShowDto getFilmShowById(Integer Id);
}
