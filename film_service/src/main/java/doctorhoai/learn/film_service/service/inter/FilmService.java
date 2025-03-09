package doctorhoai.learn.film_service.service.inter;

import doctorhoai.learn.film_service.dto.FilmDto;
import doctorhoai.learn.film_service.dto.request.FilmRequest;

import java.time.LocalDate;
import java.util.List;

public interface FilmService {
    FilmDto addFilm(FilmRequest film);
    FilmDto updateFilm(String id, FilmRequest film);
    void deleteFilm(String id);
    void activeFilm(String id);
    FilmDto getFilm(String id);
    List<FilmDto> getFilms();
    List<FilmDto> getFilmByCustom(String limit, String page, String asc, String orderBy, String q, String active);
    List<FilmDto> getFilmBySearch(String branchId, LocalDate time);
    List<FilmDto> getFilmByStatus(String status);
}