package doctorhoai.learn.paymentservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import doctorhoai.learn.paymentservice.dto.*;
import doctorhoai.learn.paymentservice.dto.response.Response;
import doctorhoai.learn.paymentservice.entity.*;
import doctorhoai.learn.paymentservice.exception.*;
import doctorhoai.learn.paymentservice.helper.MapperToObject;
import doctorhoai.learn.paymentservice.repository.*;
import doctorhoai.learn.paymentservice.service.feign.DishFeign;
import doctorhoai.learn.paymentservice.service.feign.FilmFeign;
import doctorhoai.learn.paymentservice.service.feign.FilmShowTimeFeign;
import doctorhoai.learn.paymentservice.service.feign.RoomFeign;
import doctorhoai.learn.paymentservice.service.inter.BillService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final TicketRepository ticketRepository;
    private final BillChairRepository billChairRepository;
    private final DishFeign dishFeign;
    private final BillDishRepository billDishRepository;
    private final FilmShowTimeFeign filmShowTimeFeign;
    private final RoomFeign roomFeign;
    private final PaymentMethodRepository paymentMethodRepository;
    private final FilmFeign filmFeign;
    private final MapperToObject mapperToObject;


    @Transactional
    @Override
    public BillDto createBill(BillDto billDto){
        try {
            // kiem tra truoc khi tao
            billDto.getChairs().forEach( item -> {
                Optional<BillChair> billChair = billChairRepository.getBillChairByChairCode(item.getChairCode());
                if( billChair.isPresent() ){
                    Bill bill = billChair.get().getBillChairId();
                    if(bill.getTimestamp().toLocalDate().isEqual(LocalDate.now())
                            && bill.getFilmShowTimeId().equals(billDto.getFilmShowTimeId())
                            && bill.getActive() == Active.ACTIVE
                    ){
                        throw new ErrorException("Bill existed");
                    }
                }
            });

            //call showtime
            ResponseEntity<Response> responseShowTime = filmShowTimeFeign.getFilmShowByRoomAndId(billDto.getRoomId(), billDto.getFilmShowTimeId());
            if (responseShowTime.getStatusCode() != HttpStatus.OK) {
                log.error("Room or Show Time not found");
                throw new ShowTimNotFound("Show Time not found");
            }
            ObjectMapper objectMapper = new ObjectMapper()
                    .registerModule(new ParameterNamesModule())
                    .registerModule(new Jdk8Module())
                    .registerModule(new JavaTimeModule());
            FilmShowDto filmShowDto = objectMapper.convertValue(responseShowTime.getBody().getData(), FilmShowDto.class);
            //call room
            ResponseEntity<Response> responseRoom = roomFeign.getRoomById(billDto.getRoomId());
            if (responseRoom.getStatusCode() != HttpStatus.OK) {
                log.error("Room not found");
                throw new RoomNotFound("Room not found");
            }
            RoomDto roomDto = objectMapper.convertValue(responseRoom.getBody().getData(), RoomDto.class);
            //call film
            ResponseEntity<Response> responseFilm = filmFeign.getFilmById(billDto.getFilmId());
            if (responseFilm.getStatusCode() != HttpStatus.OK) {
                log.error("Film not found");
                throw new FilmNotFound("Film not found");
            }
            FilmDto filmDto = objectMapper.convertValue(responseFilm.getBody().getData(), FilmDto.class);

            Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(billDto.getPaymentMethodId());
            if (paymentMethod.isEmpty()) {
                log.error("Payment method not found");
                throw new PaymentNotFound("Payment method not found");
            }
            Bill bill = Bill
                    .builder()
                    .totalPrice(billDto.getTotalPrice())
                    .transactionCode(billDto.getTransactionCode())
                    .filmShowTimeId(billDto.getFilmShowTimeId())
                    .paymentMethodId(paymentMethod.get())
                    .active(Active.ACTIVE)
                    .timestamp(LocalDateTime.now())
                    .status(Status.CREATED)
                    .build();
            Bill billSaved = billRepository.save(bill);
            // convert bill dto
            BillDto billConvert = BillDto
                    .builder()
                    .id(billSaved.getId())
                    .totalPrice(billSaved.getTotalPrice())
                    .transactionCode(billDto.getTransactionCode())
                    .paymentMethodId(bill.getPaymentMethodId().getId())
                    .paymentMethod(bill.getPaymentMethodId().getMethod())
                    .active(bill.getActive())
                    .timestamp(bill.getTimestamp())
                    .status(bill.getStatus().toString())
                    .filmShowTimeId(bill.getFilmShowTimeId())
                    .timeEnd(filmShowDto.getTimeEnd())
                    .timeStart(filmShowDto.getTimeStart())
                    .timeStampSee(filmShowDto.getTimestamp())
                    .roomId(roomDto.getId())
                    .nameRoom(roomDto.getName())
                    .filmId(filmDto.getId())
                    .nameFilm(filmDto.getName())
                    .build();
            List<BillChairDto> billChairDto = billDto.getChairs();
            //list return into billdto
            List<BillChairDto> billChairReturn = new ArrayList<>();
            billChairDto.forEach(item -> {
                Optional<Ticket> ticket = ticketRepository.findById(item.getTicket().getId());
                if (ticket.isEmpty()) {
                    throw new TicketNotFound("Ticket not found with id : " + item.getTicket().getId());
                }
                BillChair billChair = BillChair
                        .builder()
                        .chairCode(item.getChairCode())
                        .price(item.getPrice())
                        .ticketId(ticket.get())
                        .active(Active.ACTIVE)
                        .billChairId(billSaved)
                        .build();
                BillChair billChairSaved = billChairRepository.save(billChair);
                BillChairDto billChairTemp = BillChairDto
                        .builder()
                        .id(billChairSaved.getId())
                        .chairCode(billChairSaved.getChairCode())
                        .price(billChairSaved.getPrice())
                        .ticket(mapperToObject.mapperToTicketDto(ticket.get()))
                        .active(billChairSaved.getActive())
                        .build();
                billChairReturn.add(billChairTemp);
            });
            //convert billdto
            billConvert.setChairs(billChairReturn);

            if (!billDto.getDishes().isEmpty()) {
                List<BillDishDto> billDishes = billDto.getDishes();
                List<BillDishDto> billDishReturn = new ArrayList<>();
                billDishes.forEach(item -> {
                    ResponseEntity<Response> responseDish = dishFeign.getDishById(item.getDishDto().getId());
                    if (responseDish.getStatusCode() == HttpStatus.OK) {
                        DishDto dishDto = objectMapper.convertValue(responseDish.getBody().getData(), DishDto.class);
                        BillDish billDish = BillDish
                                .builder()
                                .active(Active.ACTIVE)
                                .price(item.getPrice())
                                .dishId(item.getDishDto().getId())
                                .billDishId(billSaved)
                                .amount(item.getAmount())
                                .build();
                        BillDish billDishSaved = billDishRepository.save(billDish);
                        BillDishDto billDishDto = BillDishDto.builder()
                                .id(billDishSaved.getId())
                                .active(billDishSaved.getActive())
                                .price(billDishSaved.getPrice())
                                .amount(billDishSaved.getAmount())
                                .dishDto(dishDto)
                                .build();
                        billDishReturn.add(billDishDto);
                    } else {
                        throw new DishNotFound("Dish not found with id : " + item.getDishDto().getId());
                    }
                });
                billConvert.setDishes(billDishReturn);
            }
            return billConvert;
        }catch (TicketNotFound t){
            log.error(t.getMessage());
            throw new TicketNotFound(t.getMessage());
        }catch (DishNotFound d){
            log.error(d.getMessage());
            throw new DishNotFound(d.getMessage());
        }catch (PaymentNotFound p){
            log.error(p.getMessage());
            throw new PaymentNotFound(p.getMessage());
        }catch ( FilmNotFound f){
            log.error(f.getMessage());
            throw new FilmNotFound(f.getMessage());
        }catch (RoomNotFound r){
            log.error(r.getMessage());
            throw new RoomNotFound(r.getMessage());
        }catch ( ShowTimNotFound s){
            log.error(s.getMessage());
            throw new ShowTimNotFound(s.getMessage());
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public List<BillDto> getAllBills() {
        List<BillDto> list = new ArrayList<>();
        List<Bill> bills = billRepository.findAll();
        bills.forEach(bill -> {
            //call showtime
            ResponseEntity<Response> responseShowTime = filmShowTimeFeign.getFilmShowTime(bill.getFilmShowTimeId());
            if (responseShowTime.getStatusCode() != HttpStatus.OK) {
                log.error("Room or Show Time not found");
                throw new ShowTimNotFound("Show Time not found");
            }
            ObjectMapper objectMapper = new ObjectMapper()
                    .registerModule(new ParameterNamesModule())
                    .registerModule(new Jdk8Module())
                    .registerModule(new JavaTimeModule());
            FilmShowDto filmShowDto = objectMapper.convertValue(responseShowTime.getBody().getData(), FilmShowDto.class);
            //call room
            ResponseEntity<Response> responseRoom = roomFeign.getRoomById(filmShowDto.getRoomId());
            if (responseRoom.getStatusCode() != HttpStatus.OK) {
                log.error("Room not found");
                throw new RoomNotFound("Room not found");
            }
            RoomDto roomDto = objectMapper.convertValue(responseRoom.getBody().getData(), RoomDto.class);
            //call film
            ResponseEntity<Response> responseFilm = filmFeign.getFilmById(filmShowDto.getFilmDto().getId());
            if (responseFilm.getStatusCode() != HttpStatus.OK) {
                log.error("Film not found");
                throw new FilmNotFound("Film not found");
            }
            FilmDto filmDto = objectMapper.convertValue(responseFilm.getBody().getData(), FilmDto.class);

            List<BillChairDto> chairs = new ArrayList<>();
            List<BillDishDto> dishes = new ArrayList<>();

            List<BillChair> listChair = billChairRepository.getBillChairByBillChairId_Id(bill.getId());
            listChair.forEach( item -> {
                BillChairDto temp = BillChairDto
                        .builder()
                        .id(item.getId())
                        .chairCode(item.getChairCode())
                        .price(item.getPrice())
                        .ticket(mapperToObject.mapperToTicketDto(item.getTicketId()))
                        .active(item.getActive())
                        .build();
                chairs.add(temp);
            });
            List<BillDish> listDish = billDishRepository.getBillDishByBillDishId_Id(bill.getId());
            listDish.forEach( item -> {
                ResponseEntity<Response> responseDish = dishFeign.getDishById(item.getDishId());
                if (responseDish.getStatusCode() == HttpStatus.OK) {
                    DishDto dishDto = objectMapper.convertValue(responseDish.getBody().getData(), DishDto.class);
                    BillDishDto temp = BillDishDto
                            .builder()
                            .id(item.getId())
                            .active(item.getActive())
                            .price(item.getPrice())
                            .amount(item.getAmount())
                            .dishDto(dishDto)
                            .build();
                    dishes.add(temp);
                }else {
                    throw new DishNotFound("Dish not found with id : " + item.getId());
                }
            });

            BillDto billDto = BillDto
                    .builder()
                    .id(bill.getId())
                    .totalPrice(bill.getTotalPrice())
                    .transactionCode(bill.getTransactionCode())
                    .paymentMethodId(bill.getPaymentMethodId().getId())
                    .paymentMethod(bill.getPaymentMethodId().getMethod())
                    .active(bill.getActive())
                    .timestamp(bill.getTimestamp())
                    .status(bill.getStatus().toString())
                    .filmShowTimeId(bill.getFilmShowTimeId())
                    .timeEnd(filmShowDto.getTimeEnd())
                    .timeStart(filmShowDto.getTimeStart())
                    .timeStampSee(filmShowDto.getTimestamp())
                    .roomId(roomDto.getId())
                    .nameRoom(roomDto.getName())
                    .filmId(filmDto.getId())
                    .nameFilm(filmDto.getName())
                    .chairs(chairs)
                    .dishes(dishes)
                    .build();
            list.add(billDto);
        });
        return list;
    }

    @Override
    public BillDto getBillById(String id) {

        Optional<Bill> billOptional = billRepository.findById(id);
        if( billOptional.isEmpty() ){
            throw new BillNotFound("Bill not found with id : " + id);
        }
        Bill bill = billOptional.get();

        //call showtime
        ResponseEntity<Response> responseShowTime = filmShowTimeFeign.getFilmShowTime(bill.getFilmShowTimeId());
        if (responseShowTime.getStatusCode() != HttpStatus.OK) {
            log.error("Room or Show Time not found");
            throw new ShowTimNotFound("Show Time not found");
        }
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
        FilmShowDto filmShowDto = objectMapper.convertValue(responseShowTime.getBody().getData(), FilmShowDto.class);
        //call room
        ResponseEntity<Response> responseRoom = roomFeign.getRoomById(filmShowDto.getRoomId());
        if (responseRoom.getStatusCode() != HttpStatus.OK) {
            log.error("Room not found");
            throw new RoomNotFound("Room not found");
        }
        RoomDto roomDto = objectMapper.convertValue(responseRoom.getBody().getData(), RoomDto.class);
        //call film
        ResponseEntity<Response> responseFilm = filmFeign.getFilmById(filmShowDto.getFilmDto().getId());
        if (responseFilm.getStatusCode() != HttpStatus.OK) {
            log.error("Film not found");
            throw new FilmNotFound("Film not found");
        }
        FilmDto filmDto = objectMapper.convertValue(responseFilm.getBody().getData(), FilmDto.class);

        List<BillChairDto> chairs = new ArrayList<>();
        List<BillDishDto> dishes = new ArrayList<>();

        List<BillChair> listChair = billChairRepository.getBillChairByBillChairId_Id(bill.getId());
        listChair.forEach( item -> {
            BillChairDto temp = BillChairDto
                    .builder()
                    .id(item.getId())
                    .chairCode(item.getChairCode())
                    .price(item.getPrice())
                    .ticket(mapperToObject.mapperToTicketDto(item.getTicketId()))
                    .active(item.getActive())
                    .build();
            chairs.add(temp);
        });
        List<BillDish> listDish = billDishRepository.getBillDishByBillDishId_Id(bill.getId());
        listDish.forEach( item -> {
            ResponseEntity<Response> responseDish = dishFeign.getDishById(item.getDishId());
            if (responseDish.getStatusCode() == HttpStatus.OK) {
                DishDto dishDto = objectMapper.convertValue(responseDish.getBody().getData(), DishDto.class);
                BillDishDto temp = BillDishDto
                        .builder()
                        .id(item.getId())
                        .active(item.getActive())
                        .price(item.getPrice())
                        .amount(item.getAmount())
                        .dishDto(dishDto)
                        .build();
                dishes.add(temp);
            }else {
                throw new DishNotFound("Dish not found with id : " + item.getId());
            }
        });

        BillDto billDto = BillDto
                .builder()
                .id(bill.getId())
                .totalPrice(bill.getTotalPrice())
                .transactionCode(bill.getTransactionCode())
                .paymentMethodId(bill.getPaymentMethodId().getId())
                .paymentMethod(bill.getPaymentMethodId().getMethod())
                .active(bill.getActive())
                .timestamp(bill.getTimestamp())
                .status(bill.getStatus().toString())
                .filmShowTimeId(bill.getFilmShowTimeId())
                .timeEnd(filmShowDto.getTimeEnd())
                .timeStart(filmShowDto.getTimeStart())
                .timeStampSee(filmShowDto.getTimestamp())
                .roomId(roomDto.getId())
                .nameRoom(roomDto.getName())
                .filmId(filmDto.getId())
                .nameFilm(filmDto.getName())
                .chairs(chairs)
                .dishes(dishes)
                .build();
        return billDto;
    }

    @Override
    public void deleteBill(String id) {
        Optional<Bill> billOptional = billRepository.findById(id);
        if( !billOptional.isEmpty() ){
            log.error("Bill not found with id : " + id);
            throw new BillNotFound("Bill not found with id : " + id);
        }
        billOptional.get().setActive(Active.INACTIVE);
        billRepository.save(billOptional.get());
    }

    @Override
    public void activeBill(String id) {
        Optional<Bill> billOptional = billRepository.findById(id);
        if( !billOptional.isEmpty() ){
            log.error("Bill not found with id : " + id);
            throw new BillNotFound("Bill not found with id : " + id);
        }
        billOptional.get().setActive(Active.ACTIVE);
        billRepository.save(billOptional.get());
    }
}
