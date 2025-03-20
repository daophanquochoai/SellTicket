package doctorhoai.learn.film_service.service.inter;

import doctorhoai.learn.film_service.dto.SubFilmDto;
import doctorhoai.learn.film_service.dto.request.SubFilmRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SubFilmService {
    SubFilmDto getSubFilmById(String id);
    SubFilmDto getSubFilmByFilmIdAndSubId(String filmId, String subId);
    Page<SubFilmDto> getSubFilmBySubId(String page, String limit, String asc, String subId);
    SubFilmDto addSubFilm(SubFilmRequest subFilmRequest);
    boolean deleteSubFilm(String filmId, String subId);
    List<SubFilmDto> getAll();
}
