package doctorhoai.learn.film_service.service.inter;

import doctorhoai.learn.film_service.dto.TypeFilmDto;
import doctorhoai.learn.film_service.dto.response.PageResponse;

import java.util.List;

public interface TypeFilmService {
    TypeFilmDto addTypeFilm(TypeFilmDto typeFilmDto);
    TypeFilmDto updateTypeFilm(String id, TypeFilmDto typeFilmDto);
    TypeFilmDto getTypeFilmById(String id);
    List<TypeFilmDto> getAllTypeFilm();
    void deleteTypeFilm(String id);
    void activeTypeFilm(String id);
    PageResponse getTypeFilmByCustom(String page, String limit, String q, String orderBy, String status, String asc);
}
