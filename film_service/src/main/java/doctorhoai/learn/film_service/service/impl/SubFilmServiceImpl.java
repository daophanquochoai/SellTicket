package doctorhoai.learn.film_service.service.impl;

import doctorhoai.learn.film_service.dto.FilmDto;
import doctorhoai.learn.film_service.dto.SubFilmDto;
import doctorhoai.learn.film_service.entity.Film;
import doctorhoai.learn.film_service.entity.SubFilm;
import doctorhoai.learn.film_service.exception.SubFilmNotFound;
import doctorhoai.learn.film_service.repository.SubFilmRepository;
import doctorhoai.learn.film_service.service.inter.SubFilmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubFilmServiceImpl implements SubFilmService {

    private final SubFilmRepository subFilmRepository;

    @Override
    public SubFilmDto getSubFilmById(String id) {
        Optional<SubFilm> subFilm = subFilmRepository.findById(id);
        if ( subFilm.isEmpty() ){
            throw new SubFilmNotFound("Sub film not found");
        }
        SubFilm subF = subFilm.get();
        Film film = subF.getFilmId();
        FilmDto filmDto = FilmDto.builder()
                .id(film.getId())
                .name(film.getName())
                .age(film.getAge())
                .description(film.getDescription())
                .content(film.getContent())
                .trailer(film.getTrailer())
                .build();
        SubFilmDto subFDto = SubFilmDto.builder()
                .id(subF.getId())
                .filmDto(filmDto)
                .build();
        return subFDto;
    }

    @Override
    public SubFilmDto getSubFilmByFilmIdAndSubId(String filmId, String subId) {
        Optional<SubFilm> subFilm = subFilmRepository.getSubFilmsByFilmId_IdAndSubId_Id(filmId, subId);
        if ( subFilm.isEmpty() ){
            throw new SubFilmNotFound("Sub film not found");
        }
        Film film = subFilm.get().getFilmId();
        FilmDto filmDto = FilmDto.builder()
                .id(film.getId())
                .name(film.getName())
                .age(film.getAge())
                .description(film.getDescription())
                .content(film.getContent())
                .trailer(film.getTrailer())
                .build();
        SubFilmDto subFDto = SubFilmDto.builder()
                .id(subFilm.get().getId())
                .filmDto(filmDto)
                .build();
        return subFDto;
    }
}
