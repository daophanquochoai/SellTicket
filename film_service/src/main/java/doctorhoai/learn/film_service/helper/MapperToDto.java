package doctorhoai.learn.film_service.helper;

import doctorhoai.learn.film_service.dto.FilmDto;
import doctorhoai.learn.film_service.dto.SubDto;
import doctorhoai.learn.film_service.dto.TypeFilmDto;
import doctorhoai.learn.film_service.entity.Film;
import doctorhoai.learn.film_service.entity.Status;
import doctorhoai.learn.film_service.entity.Sub;
import doctorhoai.learn.film_service.entity.TypeFilm;

public class MapperToDto {

    public static Film DtoToFilm(FilmDto dto){
            return Film.builder()
                    .id(dto.getId())
                    .name(dto.getName())
                    .age(dto.getAge())
                    .image(dto.getImage())
                    .duration(dto.getDuration())
                    .nation(dto.getNation())
                    .description(dto.getDescription())
                    .content(dto.getContent())
                    .trailer(dto.getTrailer())
                    .typeFilms(
                            dto.getTypeFilms().stream().map(MapperToDto::DtoToTypeFilm).toList()
                    )
                    .status(Status.valueOf(dto.getStatus().toUpperCase()))
                    .build();
    }
    public static FilmDto FilmToDto(Film film){
        return FilmDto.builder()
                .id(film.getId())
                .name(film.getName())
                .age(film.getAge())
                .image(film.getImage())
                .duration(film.getDuration())
                .nation(film.getNation())
                .description(film.getDescription())
                .content(film.getContent())
                .trailer(film.getTrailer())
                .typeFilms(
                        film.getTypeFilms().stream().map(MapperToDto::TypeFilmToDto).toList()
                )
                .status(film.getStatus().toString())
                .build();
    }
    public static TypeFilm DtoToTypeFilm(TypeFilmDto dto){
        if( dto == null ) return null;
        return TypeFilm.builder()
                .id(dto.getId())
                .name(dto.getName())
                .active(Status.valueOf(dto.getActive().toUpperCase()))
                .build();
    }
    public static TypeFilmDto TypeFilmToDto(TypeFilm film){
        if( film == null ) return null;
        return TypeFilmDto.builder()
                .id(film.getId())
                .name(film.getName())
                .active(film.getActive().toString())
                .build();
    }

    public static SubDto DtoToSub(Sub sub){
        return SubDto.builder()
                .id(sub.getId())
                .name(sub.getSub())
                .build();
    }

    public static Sub DtoToSub(SubDto sub){
        return Sub.builder()
                .id(sub.getId())
                .sub(sub.getName())
                .build();
    }
}
