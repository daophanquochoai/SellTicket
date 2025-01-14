package doctorhoai.learn.rateservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import doctorhoai.learn.rateservice.dto.CustomerDto;
import doctorhoai.learn.rateservice.dto.RateFilmDto;
import doctorhoai.learn.rateservice.dto.request.RateFilmRequest;
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

            //fetch film
            ResponseEntity<Response> filmResponse = filmFeignClient.getFilmById(filmId);
            if( filmResponse == null ){
                throw new FilmNotFound("Film not found with id : " + rate.getFilmId());
            }
            if( filmResponse.getBody().getData() == null ){
                throw new FilmNotFound("Film not found with id : " + rate.getFilmId());
            }

            RateFilm rateFilm = RateFilm.builder()
                    .filmId(filmId)
                    .content(rate.getContent())
                    .star(rate.getStar())
                    .timeStamp(LocalDateTime.now())
                    .customerId(userId)
                    .active(Status.ACTIVE)
                    .build();
            RateFilm rateFilmSaved = rateRepository.save(rateFilm);
            return MapperToDto.RateToDto(rateFilmSaved);
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
    public List<RateFilmDto> getRateByFilmId(String filmId) {
        try{
            List<RateFilm> rateFilms = rateRepository.findRateFilmByFilmId(filmId);
            List<RateFilmDto> returnValue = new ArrayList<>();
            rateFilms.forEach(
                    rate -> {
                        RateFilmDto rateFilmDto = MapperToDto.RateToDto(rate);
                        if( rateFilmDto.getActive() == Status.ACTIVE ){
                            ResponseEntity<Response> customerResponse = userFeignClient.getCustomerById(rate.getCustomerId());
                            if (customerResponse == null) {
                                throw new CustomerNotFound("Customer not found with id : " + rate.getCustomerId());
                            }
                            if (customerResponse.getBody().getStatusCode() != 200) {
                                throw new CustomerNotFound("Customer not found with id : " + rate.getCustomerId());
                            }

                            Object customerData = customerResponse.getBody().getData(); // Dữ liệu có thể là Map, nên không ép kiểu trực tiếp

                            // Kiểm tra nếu customerData là kiểu Map và chuyển nó thành CustomerDto
                            if (customerData instanceof LinkedHashMap) {
                                LinkedHashMap<String, Object> dataMap = (LinkedHashMap<String, Object>) customerData;
                                // Chuyển đổi map thành CustomerDto
                                CustomerDto customerDto = CustomerDto.builder()
                                        .id((String) dataMap.get("id"))
                                        .name((String) dataMap.get("name"))
                                        .phoneNumber((String) dataMap.get("phoneNumber"))
                                        .email((String) dataMap.get("email"))
                                        .status(Status.valueOf(dataMap.get("status").toString().toUpperCase()))
                                        .build();

                                // Xử lý customerDto
                                if(customerDto.getStatus() == Status.ACTIVE){
                                    rateFilmDto.setCustomer(customerDto);
                                }
                            } else {
                                throw new CustomerNotFound("Customer data format is incorrect.");
                            }

                            returnValue.add(rateFilmDto);
                        }
                    }
            );
            return returnValue;
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }
}
