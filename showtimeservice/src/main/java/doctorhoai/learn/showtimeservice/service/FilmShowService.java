package doctorhoai.learn.showtimeservice.service;

import doctorhoai.learn.showtimeservice.dto.FilmShowDto;

public interface FilmShowService {
    FilmShowDto addFilmShow(FilmShowDto filmShowDto);
    FilmShowDto updateFilmShow(FilmShowDto filmShowDto);
    FilmShowDto deleteFilmShow(FilmShowDto filmShowDto);

}
