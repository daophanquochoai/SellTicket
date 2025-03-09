package doctorhoai.learn.film_service.service.inter;

import doctorhoai.learn.film_service.dto.SubFilmDto;

public interface SubFilmService {
    SubFilmDto getSubFilmById(String id);
    SubFilmDto getSubFilmByFilmIdAndSubId(String filmId, String subId);
}
