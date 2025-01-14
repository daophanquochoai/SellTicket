package doctorhoai.learn.film_service.service.inter;

import doctorhoai.learn.film_service.dto.FilmDto;
import doctorhoai.learn.film_service.dto.request.FilmRequest;
import doctorhoai.learn.film_service.entity.Film;

import java.util.List;

public interface FilmService {
    FilmDto addFilm(FilmRequest film);
    FilmDto updateFilm(String id, FilmRequest film);
    void deleteFilm(String id);
    void activeFilm(String id);
    FilmDto getFilm(String id);
    List<FilmDto> getFilms();
}
