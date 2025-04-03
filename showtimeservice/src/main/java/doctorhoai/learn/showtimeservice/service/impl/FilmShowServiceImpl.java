package doctorhoai.learn.showtimeservice.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import doctorhoai.learn.showtimeservice.dto.*;
import doctorhoai.learn.showtimeservice.dto.request.FilmShowRequest;
import doctorhoai.learn.showtimeservice.dto.response.Response;
import doctorhoai.learn.showtimeservice.entity.FilmShowTime;
import doctorhoai.learn.showtimeservice.entity.Status;
import doctorhoai.learn.showtimeservice.exception.ErrorException;
import doctorhoai.learn.showtimeservice.exception.FilmShowTimeNotFound;
import doctorhoai.learn.showtimeservice.facade.RoomAsync;
import doctorhoai.learn.showtimeservice.facade.SubFilmAsync;
import doctorhoai.learn.showtimeservice.helper.MapperObject;
import doctorhoai.learn.showtimeservice.repository.FilmShowRepository;
import doctorhoai.learn.showtimeservice.service.FilmShowService;
import doctorhoai.learn.showtimeservice.service.client.feign.FilmFeign;
import doctorhoai.learn.showtimeservice.service.client.feign.PaymentFeign;
import doctorhoai.learn.showtimeservice.service.client.feign.RoomFeign;
import doctorhoai.learn.showtimeservice.service.client.feign.SubFilmFeign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmShowServiceImpl implements FilmShowService {

    private final FilmShowRepository filmShowRepository;
    private final FilmFeign filmFeign;
    private final RoomFeign roomFeign;
    private final SubFilmFeign subFilmFeign;
    private final PaymentFeign paymentFeign;
    private final RoomAsync roomAsync;
    private final SubFilmAsync subFilmAsync;

    @Override
    public FilmShowDto addFilmShow(FilmShowRequest filmShowRequest) {

//        Response responseRoom = roomFeign.getRoomById(filmShowRequest.getRoomId()).getBody();
        //async
        CompletableFuture<ResponseEntity<Response>> responseAsync = roomAsync.getRoomById(filmShowRequest.getRoomId());

        FilmShowTime filmShowTime = FilmShowTime.builder()
                .timeEnd(filmShowRequest.getTimeEnd())
                .timeStart(filmShowRequest.getTimeStart())
                .timestamp(filmShowRequest.getTimestamp())
                .status(Status.valueOf(filmShowRequest.getStatus().toUpperCase()))
                .build();

        //wait
        CompletableFuture.allOf(responseAsync);

        Response responseRoom = responseAsync.join().getBody();
        if( responseRoom.getStatusCode() == 200 ){
            filmShowTime.setRoomId(filmShowRequest.getRoomId());
        }else{
            throw new ErrorException("Room Service Down");
        }
        ResponseEntity<Response> responseSubFilm = subFilmFeign.getSubFilmById(filmShowRequest.getSubFilmId());
        if( responseSubFilm.getStatusCode() != HttpStatusCode.valueOf(200)){
            throw new ErrorException("Film service down");
        }else{
            filmShowTime.setSubFilmId(filmShowRequest.getSubFilmId());
        }
        FilmShowTime filmShowTimeSaved = filmShowRepository.save(filmShowTime);
        return MapperObject.mapToFilmShowDto(filmShowTimeSaved);
    }

    @Override
    public FilmShowDto updateFilmShow(Integer id ,FilmShowRequest filmShowRequest) {
//        Optional<FilmShowTime> filmShowTimeOptional = filmShowRepository.findById(id);
//        if ( filmShowTimeOptional.isEmpty() ){
//            throw new FilmShowTimeNotFound("Film Show Time not found with id : " + id);
//        }
//        FilmShowTime filmShowTimeOld = filmShowTimeOptional.get();
//        filmShowTimeOld.setId(id);
//        filmShowTimeOld.setTimeEnd(filmShowRequest.getTimeEnd());
//        filmShowTimeOld.setTimeStart(filmShowRequest.getTimeStart());
//        filmShowTimeOld.setTimestamp(filmShowRequest.getTimestamp());
//        filmShowTimeOld.setStatus(Status.valueOf(filmShowRequest.getStatus().toUpperCase()));
//        if( filmShowTimeOld.getFilmId() != filmShowRequest.getFilmId() ){
//            try{
//                Response responseFilm = filmFeign.getFilmById(filmShowRequest.getFilmId()).getBody();
//                if( responseFilm.getStatusCode() == 200 ){
//                    filmShowTimeOld.setFilmId(filmShowRequest.getFilmId());
//                }
//            }catch (Exception e){
//                log.info(e.getMessage());
//                throw new ErrorException("Film not found with id : " + filmShowRequest.getFilmId());
//            }
//        }
//        if( filmShowTimeOld.getRoomId() != filmShowRequest.getRoomId() ){
//            try{
//                Response responseRoom = roomFeign.getRoomById(filmShowRequest.getRoomId()).getBody();
//                if( responseRoom.getStatusCode() == 200 ){
//                    filmShowTimeOld.setRoomId(filmShowRequest.getRoomId());
//                }
//            }catch (Exception e){
//                log.info(e.getMessage());
//                throw new ErrorException("Room not found with id : " + filmShowRequest.getRoomId());
//            }
//        }
//        FilmShowTime filmShowTimeSaved = filmShowRepository.save(filmShowTimeOld);
//        return MapperObject.mapToFilmShowDto(filmShowTimeSaved);
        return null;
    }

    @Override
    public void deleteFilmShow( Integer id) {
        Optional<FilmShowTime> filmShowTimeOptional = filmShowRepository.findById(id);
        if( filmShowTimeOptional.isEmpty() ){
            throw new FilmShowTimeNotFound("Film Show Time not found with id : " + id);
        }
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
        ResponseEntity<Response> response = paymentFeign.getBillByFilmShowId(id);
        if( response.getStatusCode() != HttpStatus.OK){
            throw new ErrorException("Payment Server Down!");
        }
        List<BillDto> list = objectMapper.convertValue(response.getBody().getData(), new TypeReference<List<BillDto>>() {});
        if(!list.isEmpty()){
            throw new ErrorException("Không thể xóa vì đã sử dụng");
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
    public List<FilmShowManage> getFilmShows(String roomId, LocalDate date) {
        List<FilmShowTime> list = filmShowRepository.getFilmShowTimeByRoomIdAndTimestampAndStatus(roomId,date,Status.ACTIVE);
        return list.stream().map(item -> {
            ResponseEntity<Response> responseSubFilm = subFilmFeign.getSubFilmById(item.getSubFilmId());
            if( responseSubFilm.getStatusCode() != HttpStatusCode.valueOf(200)){
                throw new ErrorException("Film service down");
            }
            ObjectMapper objectMapper = new ObjectMapper()
                    .registerModule(new ParameterNamesModule())
                    .registerModule(new Jdk8Module())
                    .registerModule(new JavaTimeModule());
            SubFilmDto subFilmDto = objectMapper.convertValue(responseSubFilm.getBody().getData(), SubFilmDto.class);
            return FilmShowManage.builder()
                    .filmName(subFilmDto.getFilmDto().getName())
                    .timeEnd(item.getTimeEnd())
                    .timeStart(item.getTimeStart())
                    .subName(subFilmDto.getSubDto().getName())
                    .timestamp(item.getTimestamp())
                    .id(item.getId())
                    .roomId(roomId)
                    .status(item.getStatus().toString())
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public FilmShowDto getFilmShowByRoomIdAndFilmShowDto(String roomId, Integer Id) {
        Optional<FilmShowTime> filmShowTimeOptional = filmShowRepository.getFilmShowTimeByRoomIdAndIdAndStatus(roomId, Id, Status.ACTIVE);
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

    @Override
    public List<FilmShowDto> getFilmShowsByBranch(String branchId, LocalDate date, String filmId, String subId) {
        try{
            ObjectMapper objectMapper = new ObjectMapper()
                    .registerModule(new ParameterNamesModule())
                    .registerModule(new Jdk8Module())
                    .registerModule(new JavaTimeModule());
            //async
            CompletableFuture<ResponseEntity<Response>> responseAsyncSubFilm = subFilmAsync.getSubFilmByFilmIdAndSubId(filmId,subId);
            CompletableFuture<ResponseEntity<Response>> responseAsyncRoom = roomAsync.getRoomByBranch(branchId);
//            ResponseEntity<Response> response = roomFeign.getRoomByBranch(branchId);
//            ResponseEntity<Response> responseSubFilm = subFilmFeign.getSubFilmByFilmIdAndSubId(filmId, subId);
            //wait
            CompletableFuture.allOf(responseAsyncSubFilm, responseAsyncRoom);
            ResponseEntity<Response> responseSubFilm = responseAsyncSubFilm.join();
            ResponseEntity<Response> responseRoom = responseAsyncRoom.join();

            if( responseSubFilm.getStatusCode() != HttpStatusCode.valueOf(200)){
                throw new ErrorException("Film service down");
            }
            SubFilmDto subFilmDto = objectMapper.convertValue(responseSubFilm.getBody().getData(), SubFilmDto.class);
            List<FilmShowTime> list = filmShowRepository.getShowTimeByTimestampAndSubFilmIdAndStatus(date, subFilmDto.getId(), Status.ACTIVE);
            List<RoomDto> listRoom;
            if( responseRoom.getStatusCode() != HttpStatusCode.valueOf(200)){
                throw new ErrorException("Room service down");
            }
            String json = objectMapper.writeValueAsString(responseRoom.getBody().getData());
            listRoom = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, RoomDto.class));
            list = list.stream().filter( item -> {
                for (RoomDto roomDto : listRoom) {
                    if( roomDto.getId().equals(item.getRoomId())){
                        return true;
                    }
                }
                return false;
            }).toList();
            return list.stream().map(MapperObject::mapToFilmShowDto).toList();
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException(ex.getCause());
        }
    }

    @Override
    public List<FilmShowDto> getFilmShowBySubFilm(String subFilmId) {
        List<FilmShowTime> list = filmShowRepository.getFilmShowTimeBySubFilmId(subFilmId);
        return list.stream().map(MapperObject::mapToFilmShowDto).collect(Collectors.toList());
    }
}
