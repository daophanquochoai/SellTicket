package doctorhoai.learn.showtimeservice.helper;


import doctorhoai.learn.showtimeservice.dto.FilmDto;
import doctorhoai.learn.showtimeservice.dto.FilmShowDto;
import doctorhoai.learn.showtimeservice.entity.FilmShowTime;
import doctorhoai.learn.showtimeservice.entity.Status;

public class MapperObject {
    public static FilmShowDto mapToFilmShowDto(FilmShowTime filmShowTime){

        return FilmShowDto
                .builder()
                .id(filmShowTime.getId())
                .timeEnd(filmShowTime.getTimeEnd())
                .timeStart(filmShowTime.getTimeStart())
                .filmDto(FilmDto.builder().id(filmShowTime.getFilmId()).build())
                .roomId(filmShowTime.getRoomId())
                .timestamp(filmShowTime.getTimestamp())
                .status(filmShowTime.getStatus().toString())
                .build();

    }
    public static FilmShowTime mapToFilmShow(FilmShowDto filmShowDto){

        return FilmShowTime
                .builder()
                .id(filmShowDto.getId())
                .timeEnd(filmShowDto.getTimeEnd())
                .timeStart(filmShowDto.getTimeStart())
                .timestamp(filmShowDto.getTimestamp())
                .filmId(filmShowDto.getFilmDto().getId())
                .roomId(filmShowDto.getRoomId())
                .status(Status.valueOf(filmShowDto.getStatus().toUpperCase()))
                .build();

    }
}
