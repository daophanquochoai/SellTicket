package doctorhoai.learn.rateservice.helper;

import doctorhoai.learn.rateservice.dto.CustomerDto;
import doctorhoai.learn.rateservice.dto.FilmDto;
import doctorhoai.learn.rateservice.dto.RateFilmDto;
import doctorhoai.learn.rateservice.entity.RateFilm;

public class MapperToDto {
    public static RateFilm DtoToRate(RateFilmDto dto){
        return RateFilm.builder()
                .id(dto.getId())
                .star(dto.getStar())
                .content(dto.getContent())
                .timeStamp(dto.getTimeStamp())
                .customerId(dto.getCustomer() == null ? null : dto.getCustomer().getId())
                .filmId(dto.getFilm() == null ? null : dto.getFilm().getId())
                .build();
    }
    public static RateFilmDto RateToDto(RateFilm rate){
        return RateFilmDto.builder()
                .id(rate.getId())
                .star(rate.getStar())
                .content(rate.getContent())
                .timeStamp(rate.getTimeStamp())
                .customer(
                        CustomerDto.builder()
                                .id(rate.getCustomerId())
                                .build()
                )
                .film(
                        FilmDto.builder()
                                .id(rate.getFilmId())
                                .build()
                )
                .build();
    }
}
