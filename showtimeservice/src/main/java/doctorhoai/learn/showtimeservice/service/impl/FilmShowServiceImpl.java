package doctorhoai.learn.showtimeservice.service.impl;

import doctorhoai.learn.showtimeservice.dto.FilmShowDto;
import doctorhoai.learn.showtimeservice.dto.request.FilmShowRequest;
import doctorhoai.learn.showtimeservice.dto.response.Response;
import doctorhoai.learn.showtimeservice.entity.Film;
import doctorhoai.learn.showtimeservice.entity.FilmShowTime;
import doctorhoai.learn.showtimeservice.entity.Room;
import doctorhoai.learn.showtimeservice.entity.Status;
import doctorhoai.learn.showtimeservice.exception.ErrorException;
import doctorhoai.learn.showtimeservice.exception.FilmShowTimeNotFound;
import doctorhoai.learn.showtimeservice.helper.MapperObject;
import doctorhoai.learn.showtimeservice.repository.FilmShowRepository;
import doctorhoai.learn.showtimeservice.service.FilmShowService;
import doctorhoai.learn.showtimeservice.service.client.feign.FilmFeign;
import doctorhoai.learn.showtimeservice.service.client.feign.RoomFeign;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmShowServiceImpl implements FilmShowService {

    private final FilmShowRepository filmShowRepository;
    private final FilmFeign filmFeign;
    private final RoomFeign roomFeign;

    @Override
    public FilmShowDto addFilmShow(FilmShowRequest filmShowRequest) {

        Response responseFilm = filmFeign.getFilmById(filmShowRequest.getFilmId()).getBody();
        Response responseRoom = roomFeign.getRoomById(filmShowRequest.getRoomId()).getBody();

        FilmShowTime filmShowTime = FilmShowTime.builder()
                .timeEnd(filmShowRequest.getTimeEnd())
                .timeStart(filmShowRequest.getTimeStart())
                .timestamp(filmShowRequest.getTimestamp())
                .status(Status.valueOf(filmShowRequest.getStatus().toUpperCase()))
                .build();
        if( responseFilm.getStatusCode() == 200 ){
            filmShowTime.setFilmId(filmShowRequest.getFilmId());
        }
        if( responseRoom.getStatusCode() == 200 ){
            filmShowTime.setRoomId(filmShowRequest.getRoomId());
        }
        FilmShowTime filmShowTimeSaved = filmShowRepository.save(filmShowTime);
        return MapperObject.mapToFilmShowDto(filmShowTimeSaved);
    }

    @Override
    public FilmShowDto updateFilmShow(Integer id ,FilmShowRequest filmShowRequest) {
        Optional<FilmShowTime> filmShowTimeOptional = filmShowRepository.findById(id);
        if ( filmShowTimeOptional.isEmpty() ){
            throw new FilmShowTimeNotFound("Film Show Time not found with id : " + id);
        }
        FilmShowTime filmShowTimeOld = filmShowTimeOptional.get();
        filmShowTimeOld.setId(id);
        filmShowTimeOld.setTimeEnd(filmShowRequest.getTimeEnd());
        filmShowTimeOld.setTimeStart(filmShowRequest.getTimeStart());
        filmShowTimeOld.setTimestamp(filmShowRequest.getTimestamp());
        filmShowTimeOld.setStatus(Status.valueOf(filmShowRequest.getStatus().toUpperCase()));
        if( filmShowTimeOld.getFilmId() != filmShowRequest.getFilmId() ){
            try{
                Response responseFilm = filmFeign.getFilmById(filmShowRequest.getFilmId()).getBody();
                if( responseFilm.getStatusCode() == 200 ){
                    filmShowTimeOld.setFilmId(filmShowRequest.getFilmId());
                }
            }catch (Exception e){
                log.info(e.getMessage());
                throw new ErrorException("Film not found with id : " + filmShowRequest.getFilmId());
            }
        }
        if( filmShowTimeOld.getRoomId() != filmShowRequest.getRoomId() ){
            try{
                Response responseRoom = roomFeign.getRoomById(filmShowRequest.getRoomId()).getBody();
                if( responseRoom.getStatusCode() == 200 ){
                    filmShowTimeOld.setRoomId(filmShowRequest.getRoomId());
                }
            }catch (Exception e){
                log.info(e.getMessage());
                throw new ErrorException("Room not found with id : " + filmShowRequest.getRoomId());
            }
        }
        FilmShowTime filmShowTimeSaved = filmShowRepository.save(filmShowTimeOld);
        return MapperObject.mapToFilmShowDto(filmShowTimeSaved);
    }

    @Override
    public void deleteFilmShow( Integer id) {
        Optional<FilmShowTime> filmShowTimeOptional = filmShowRepository.findById(id);
        if( filmShowTimeOptional.isEmpty() ){
            throw new FilmShowTimeNotFound("Film Show Time not found with id : " + id);
        }
        filmShowTimeOptional.get().setStatus(Status.DELETE);
        filmShowRepository.save(filmShowTimeOptional.get());
    }

    @Override
    public void activeFilmShow(Integer id) {
        Optional<FilmShowTime> filmShowTimeOptional = filmShowRepository.findById(id);
        if( filmShowTimeOptional.isEmpty() ){
            throw new FilmShowTimeNotFound("Film Show Time not found with id : " + id);
        }
        filmShowTimeOptional.get().setStatus(Status.ACTIVE);
        filmShowRepository.save(filmShowTimeOptional.get());
    }

    @Override
    public List<FilmShowDto> getFilmShows(String roomId, LocalDate date) {
        List<FilmShowTime> list = filmShowRepository.getFilmShowTimeByRoomIdAndTimestamp(roomId,date);
        return list.stream().map(MapperObject::mapToFilmShowDto).collect(Collectors.toList());
    }

    @Override
    public FilmShowDto getFilmShowByRoomIdAndFilmShowDto(String roomId, Integer Id) {
        Optional<FilmShowTime> filmShowTimeOptional = filmShowRepository.getFilmShowTimeByRoomIdAndId(roomId, Id);
        if( filmShowTimeOptional.isEmpty()){
            throw new FilmShowTimeNotFound("Film Show Not Found with id : " + Id);
        }
        return MapperObject.mapToFilmShowDto(filmShowTimeOptional.get());
    }

    @Override
    public FilmShowDto getFilmShowById(Integer Id) {
        Optional<FilmShowTime> filmShowTimeOptional = filmShowRepository.findById(Id);
        if( filmShowTimeOptional.isEmpty()){
            throw new FilmShowTimeNotFound("Film Show Not Found with id : " + Id);
        }
        return MapperObject.mapToFilmShowDto(filmShowTimeOptional.get());
    }
}
