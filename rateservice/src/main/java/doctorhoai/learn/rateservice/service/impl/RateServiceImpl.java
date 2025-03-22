package doctorhoai.learn.rateservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import doctorhoai.learn.rateservice.dto.CustomerDto;
import doctorhoai.learn.rateservice.dto.FilmDto;
import doctorhoai.learn.rateservice.dto.RateFilmDto;
import doctorhoai.learn.rateservice.dto.request.RateFilmRequest;
import doctorhoai.learn.rateservice.dto.response.PageObject;
import doctorhoai.learn.rateservice.dto.response.RateForFilm;
import doctorhoai.learn.rateservice.dto.response.Response;
import doctorhoai.learn.rateservice.entity.RateFilm;
import doctorhoai.learn.rateservice.entity.Status;
import doctorhoai.learn.rateservice.exception.CustomerNotFound;
import doctorhoai.learn.rateservice.exception.ErrorException;
import doctorhoai.learn.rateservice.exception.FilmNotFound;
import doctorhoai.learn.rateservice.exception.RateNotFound;
import doctorhoai.learn.rateservice.feignclient.FilmFeignClient;
import doctorhoai.learn.rateservice.feignclient.UserFeignClient;
import doctorhoai.learn.rateservice.helper.MapperToDto;
import doctorhoai.learn.rateservice.repository.RateRepository;
import doctorhoai.learn.rateservice.service.inter.RateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RateServiceImpl implements RateService {
    private final RateRepository rateRepository;
    private final UserFeignClient userFeignClient;
    private final FilmFeignClient filmFeignClient;

    @Override
    public RateFilmDto addRateFilm(String userId, String filmId,RateFilmRequest rate) {
        try{
            //fetch customer
            ResponseEntity<Response> customerResponse = userFeignClient.getCustomerById(userId);
            if( customerResponse == null ){
                throw new CustomerNotFound("Customer not found with id : " + rate.getCustomerId());
            }
            if(customerResponse.getBody().getData() == null ){
                throw new CustomerNotFound("Customer not found with id : " + rate.getCustomerId());
            }
            ObjectMapper objectMapper = new ObjectMapper()
                    .registerModule(new ParameterNamesModule())
                    .registerModule(new Jdk8Module())
                    .registerModule(new JavaTimeModule());
            CustomerDto customerDto = objectMapper.convertValue(customerResponse.getBody().getData(), CustomerDto.class);
            //fetch film
            ResponseEntity<Response> filmResponse = filmFeignClient.getFilmById(filmId);
            if( filmResponse == null ){
                throw new FilmNotFound("Film not found with id : " + rate.getFilmId());
            }
            if( filmResponse.getBody().getData() == null ){
                throw new FilmNotFound("Film not found with id : " + rate.getFilmId());
            }
            FilmDto filmDto = objectMapper.convertValue(filmResponse.getBody().getData(), FilmDto.class);

            RateFilm rateFilm = RateFilm.builder()
                    .filmId(filmId)
                    .content(rate.getContent())
                    .star(rate.getStar())
                    .timeStamp(LocalDateTime.now())
                    .customerId(userId)
                    .active(Status.ACTIVE)
                    .build();
            RateFilm rateFilmSaved = rateRepository.save(rateFilm);
            RateFilmDto rateFilmDto =  MapperToDto.RateToDto(rateFilmSaved);
            rateFilmDto.setCustomer(customerDto);
            rateFilmDto.setFilm(filmDto);
            return rateFilmDto;
        }
        catch (FilmNotFound filmNotFound){
            log.error(filmNotFound.getMessage());
            throw new FilmNotFound(filmNotFound.getMessage());
        }
        catch (CustomerNotFound customerNotFound){
            log.error(customerNotFound.getMessage());
            throw new CustomerNotFound(customerNotFound.getMessage());
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public void deleteRateFilm(String id) {
        Optional<RateFilm> rateOp = rateRepository.findById(id);
        if( rateOp.isEmpty() ){
            throw new RateNotFound("Rate not found with id : " + id);
        }
        RateFilm rate = rateOp.get();
        rate.setActive(Status.DELETE);
        try{
            rateRepository.save(rate);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public void activeRateFilm(String id) {
        Optional<RateFilm> rateOp = rateRepository.findById(id);
        if( rateOp.isEmpty() ){
            throw new RateNotFound("Rate not found with id : " + id);
        }
        RateFilm rate = rateOp.get();
        rate.setActive(Status.ACTIVE);
        try{
            rateRepository.save(rate);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public PageObject getRateByFilmId(String filmId, String limit, String page, String asc, String status, String q, String orderBy) {
        try{
            Page<RateFilm> rateFilms;
            Pageable pageable;
            if( asc.equals("asc")){
                pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(limit), Sort.by(orderBy));
            }else{
                pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(limit), Sort.by(orderBy).descending());
            }
            if( status.equals("none")){
                rateFilms = rateRepository.getRateFilmByCustom(pageable, q, filmId);
            }else{
                rateFilms = rateRepository.getRateFilmByCustom(pageable, q, Status.valueOf(status), filmId);
            }
            List<RateFilmDto> returnValue = new ArrayList<>();
            long rateSum = 0;
            ObjectMapper objectMapper = new ObjectMapper()
                    .registerModule(new ParameterNamesModule())
                    .registerModule(new Jdk8Module())
                    .registerModule(new JavaTimeModule());
            for( RateFilm rate: rateFilms ) {
                rateSum += rate.getStar();
                RateFilmDto rateFilmDto = MapperToDto.RateToDto(rate);
                if (rateFilmDto.getActive() == Status.ACTIVE) {
                    ResponseEntity<Response> customerResponse = userFeignClient.getCustomerById(rate.getCustomerId());
                    if (customerResponse == null) {
                        throw new CustomerNotFound("Customer not found with id : " + rate.getCustomerId());
                    }
                    if (customerResponse.getBody().getStatusCode() != 200) {
                        throw new CustomerNotFound("Customer not found with id : " + rate.getCustomerId());
                    }
                    CustomerDto customerDto = objectMapper.convertValue(customerResponse.getBody().getData(), CustomerDto.class);
                    ResponseEntity<Response> filmResponse = filmFeignClient.getFilmById(filmId);
                    if (filmResponse == null) {
                        throw new FilmNotFound("Film not found with id : " + rate.getFilmId());
                    }
                    if (filmResponse.getBody().getData() == null) {
                        throw new FilmNotFound("Film not found with id : " + rate.getFilmId());
                    }
                    FilmDto filmDto = objectMapper.convertValue(filmResponse.getBody().getData(), FilmDto.class);
                    // Xử lý customerDto
                    rateFilmDto.setCustomer(customerDto);
                    rateFilmDto.setFilm(filmDto);

                    returnValue.add(rateFilmDto);
                }
                ;
            }
//            if( returnValue.isEmpty() ){
//                return PageObject.builder()
//                        .pageCurrent(pageable.getPageNumber() + 1)
//                        .totalPage(rateFilms.getTotalPages())
//                        .data(new RateForFilm( 0, returnValue))
//                        .build();
//            }else{
//                long a = rateFilms.getTotalElements();
//                double rate = Math.ceil(rateSum/rateFilms.getTotalElements());
//                return PageObject.builder()
//                        .pageCurrent(pageable.getPageNumber() + 1)
//                        .totalPage(rateFilms.getTotalPages())
//                        .data( new RateForFilm( rate, returnValue))
//                        .build();
//            }
            Integer rateStar = rateRepository.getRate(filmId);
            return PageObject.builder()
                    .pageCurrent(pageable.getPageNumber() + 1)
                    .totalPage(rateFilms.getTotalPages())
                    .data(new RateForFilm( rateStar == null ? 0 : rateStar , returnValue))
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public PageObject getRateByCustom(String limit, String page, String asc, String status, String q, String orderBy) {
        Page<RateFilm> rateFilms;
        Pageable pageable;
        if( asc.equals("asc")){
            pageable = PageRequest.of(Integer.parseInt(page),Integer.parseInt(limit), Sort.by(orderBy));
        }else{
            pageable = PageRequest.of(Integer.parseInt(page),Integer.parseInt(limit), Sort.by(orderBy).descending());
        }
        if( status.equals("none")){
            rateFilms = rateRepository.getRateFilmByCustom(pageable,q);
        }else{
            rateFilms = rateRepository.getRateFilmByCustom(pageable,q,Status.valueOf(status));
        }
        List<RateFilmDto> returnValue = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
        for( RateFilm rate: rateFilms ) {
            RateFilmDto rateFilmDto = MapperToDto.RateToDto(rate);
            ResponseEntity<Response> customerResponse = userFeignClient.getCustomerById(rate.getCustomerId());
            if (customerResponse == null) {
                throw new CustomerNotFound("Customer not found with id : " + rate.getCustomerId());
            }
            if (customerResponse.getBody().getStatusCode() != 200) {
                throw new CustomerNotFound("Customer not found with id : " + rate.getCustomerId());
            }
            CustomerDto customerDto = objectMapper.convertValue(customerResponse.getBody().getData(), CustomerDto.class);
            ResponseEntity<Response> filmResponse = filmFeignClient.getFilmById(rate.getFilmId());
            if (filmResponse == null) {
                throw new FilmNotFound("Film not found with id : " + rate.getFilmId());
            }
            if (filmResponse.getBody().getData() == null) {
                throw new FilmNotFound("Film not found with id : " + rate.getFilmId());
            }
            FilmDto filmDto = objectMapper.convertValue(filmResponse.getBody().getData(), FilmDto.class);
            rateFilmDto.setCustomer(customerDto);
            rateFilmDto.setFilm(filmDto);

            returnValue.add(rateFilmDto);
        };
        return PageObject.builder()
                .pageCurrent(pageable.getPageNumber() + 1)
                .totalPage(rateFilms.getTotalPages())
                .data(returnValue)
                .build();
    }
}
