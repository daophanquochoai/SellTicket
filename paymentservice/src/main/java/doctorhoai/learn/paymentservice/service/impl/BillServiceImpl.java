package doctorhoai.learn.paymentservice.service.impl;

import com.cloudinary.Cloudinary;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import doctorhoai.learn.basedomain.Event.BillChairTicket;
import doctorhoai.learn.basedomain.Event.BillDishTicket;
import doctorhoai.learn.basedomain.Event.TicketEmail;
import doctorhoai.learn.paymentservice.dto.*;
import doctorhoai.learn.paymentservice.dto.response.Response;
import doctorhoai.learn.paymentservice.entity.*;
import doctorhoai.learn.paymentservice.exception.*;
import doctorhoai.learn.paymentservice.facade.FilmAsync;
import doctorhoai.learn.paymentservice.facade.FilmShowAsync;
import doctorhoai.learn.paymentservice.facade.RoomAsync;
import doctorhoai.learn.paymentservice.facade.SubFilmAsync;
import doctorhoai.learn.paymentservice.helper.MapperToObject;
import doctorhoai.learn.paymentservice.repository.*;
import doctorhoai.learn.paymentservice.service.feign.*;
import doctorhoai.learn.paymentservice.service.inter.BillService;
import doctorhoai.learn.paymentservice.service.producer.KafkaMessagePublish;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
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
    private final SubFilmFeign subFilmFeign;
    private final MapperToObject mapperToObject;
    private final KafkaMessagePublish kafkaMessagePublish;

    //async
    private final FilmShowAsync filmShowAsync;
    private final RoomAsync roomAsync;
    private final FilmAsync filmAsync;
    private final Executor executor;
    private final SubFilmAsync subFilmAsync;

    //cloudinary
    private final Cloudinary cloudinary;


    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = { SQLException.class })
    @Override
    public BillDto createBill(BillDto billDto){
        try {
            // kiem tra truoc khi tao
            billDto.getChairs().forEach( item -> {
                Optional<BillChair> billChair = billChairRepository.getBillChairByChairCodeAndBillChairId_FilmShowTimeId(item.getChairCode(),billDto.getFilmShowTimeId());
                if( billChair.isPresent() ){
                    Bill bill = billChair.get().getBillChairId();
                    if(bill.getTimestamp().toLocalDate().isEqual(LocalDate.now())
                            && bill.getFilmShowTimeId().equals(billDto.getFilmShowTimeId())
                            && bill.getActive() == Active.ACTIVE
                            && bill.getStatus() == Status.SUCCESS
                    ){
                        throw new ErrorException("Bill existed");
                    }
                }
            });

            //call showtime
//            ResponseEntity<Response> responseShowTime = filmShowTimeFeign.getFilmShowByRoomAndId(billDto.getRoomId(), billDto.getFilmShowTimeId());
            //call room
//            ResponseEntity<Response> responseRoom = roomFeign.getRoomById(billDto.getRoomId());
            //call film
//            ResponseEntity<Response> responseFilm = filmFeign.getFilmById(billDto.getFilmId());

            //async
            CompletableFuture<ResponseEntity<Response>> responseAsyncShowTime = filmShowAsync.getFilmShowByRoomAndId(billDto.getRoomId(),billDto.getFilmShowTimeId());
            CompletableFuture<ResponseEntity<Response>> responseAsyncRoom = roomAsync.getRoomById(billDto.getRoomId());
            CompletableFuture<ResponseEntity<Response>> responseAsyncFilm = filmAsync.getFilmById(billDto.getFilmId());

            CompletableFuture.allOf(responseAsyncRoom,responseAsyncFilm,responseAsyncShowTime);

            ResponseEntity<Response> responseShowTime = responseAsyncShowTime.join();
            ResponseEntity<Response> responseRoom = responseAsyncRoom.join();
            ResponseEntity<Response> responseFilm = responseAsyncFilm.join();

            if (responseShowTime.getStatusCode() != HttpStatus.OK) {
                log.error("Room or Show Time not found");
                throw new ShowTimNotFound("Show Time not found");
            }
            ObjectMapper objectMapper = new ObjectMapper()
                    .registerModule(new ParameterNamesModule())
                    .registerModule(new Jdk8Module())
                    .registerModule(new JavaTimeModule());
            FilmShowDto filmShowDto = objectMapper.convertValue(responseShowTime.getBody().getData(), FilmShowDto.class);

            if (responseRoom.getStatusCode() != HttpStatus.OK) {
                log.error("Room not found");
                throw new RoomNotFound("Room not found");
            }

            RoomDto roomDto = objectMapper.convertValue(responseRoom.getBody().getData(), RoomDto.class);

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
                    .userName(billDto.getUserName())
                    .email(billDto.getEmail())
                    .numberPhone(billDto.getNumberPhone())
                    .billChair(new ArrayList<>())
                    .billDish(new ArrayList<>())
                    .build();

            //convert billdto
            //list return into billdto
            List<BillChairDto> billChairReturn = new ArrayList<>();
            List<BillChairDto> billChairDto = billDto.getChairs();
            CompletableFuture<Void> billAsync = CompletableFuture.supplyAsync(()-> {
                if( !billDto.getChairs().isEmpty()){
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
                                .billChairId(bill)
                                .build();
                        TicketDto ticketDto = TicketDto.builder()
                                .id(ticket.get().getId())
                                .name(ticket.get().getName())
                                .active(ticket.get().getActive())
                                .conditionUse(ticket.get().getConditionUse())
                                .price(ticket.get().getPrice())
                                .typeTicket(ticket.get().getTypeTicket())
                                .slot(ticket.get().getSlot())
                                .build();
                        billChairReturn.add(
                                BillChairDto.builder()
                                        .active(item.getActive())
                                        .price(item.getPrice())
                                        .id(item.getId())
                                        .chairCode(item.getChairCode())
                                        .ticket(ticketDto)
                                        .build()
                        );
                        bill.getBillChair().add(billChair);
                    });
                }
                return null;
            },executor);
            // dish
            List<BillDishDto> billDishReturn = new ArrayList<>();
            CompletableFuture<Void> billDishAsync = CompletableFuture.supplyAsync(() -> {
                if (!billDto.getDishes().isEmpty()) {
                    List<BillDishDto> billDishes = billDto.getDishes();
                    billDishes.forEach(item -> {
                        ResponseEntity<Response> responseDish = dishFeign.getDishById(item.getDishDto().getId());
                        if (responseDish.getStatusCode() == HttpStatus.OK) {
                            DishDto dishDto = objectMapper.convertValue(responseDish.getBody().getData(), DishDto.class);
                            BillDish billDish = BillDish
                                    .builder()
                                    .active(Active.ACTIVE)
                                    .price(item.getPrice())
                                    .dishId(item.getDishDto().getId())
                                    .amount(item.getAmount())
                                    .billDishId(bill)
                                    .build();
                            billDishReturn.add(
                                    BillDishDto.builder()
                                            .active(item.getActive())
                                            .price(item.getPrice())
                                            .id(item.getId())
                                            .amount(item.getAmount())
                                            .dishDto(dishDto)
                                            .build()
                            );
                            bill.getBillDish().add(billDish);
                        } else {
                            throw new DishNotFound("Dish not found with id : " + item.getDishDto().getId());
                        }
                    });
                }
                return null;
            }, executor);

            CompletableFuture.allOf(billAsync,billDishAsync).join();
            Bill billSaved = billRepository.save(bill);
            billRepository.flush();
            try{
                BufferedImage qrImage = generateQRCodeImage(billSaved.toString());
                // chuyen thanh mang byte
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(qrImage, "png", baos);
                byte[] imageBytes = baos.toByteArray();
                // upload
                String url = cloudinary.uploader()
                        .upload(imageBytes, Map.of("public_id", UUID.randomUUID().toString()))
                        .get("url").toString();
                billSaved.setQrcode(url);
                billRepository.save(billSaved);
            }catch (Exception e){
                e.printStackTrace();
            }
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
                    .nameBranch(roomDto.getBranch().getNameBranch())
                    .address(roomDto.getBranch().getAddress())
                    .timeStampSee(filmShowDto.getTimestamp())
                    .roomId(roomDto.getId())
                    .nameRoom(roomDto.getName())
                    .filmId(filmDto.getId())
                    .nameFilm(filmDto.getName())
                    .userName(billSaved.getUserName())
                    .email(billSaved.getEmail())
                    .numberPhone(billSaved.getNumberPhone())
                    .build();

            billConvert.setChairs(billChairReturn);
            billConvert.setDishes(billDishReturn);
            billDto.setQrCode(billConvert.getQrCode());
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

    private static BufferedImage generateQRCodeImage(String data) throws Exception {
        int width = 300;
        int height = 300;
        BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height);
        return MatrixToImageWriter.toBufferedImage(matrix);
    }

    @Override
    public PageObject getAllBills(String page, String limit, String active, String orderBy, String asc, String q) {
        List<BillDto> list = new ArrayList<>();
        Pageable pageable;
        Page<Bill> bills;
        if( asc.equals("asc")){
            pageable = PageRequest.of(Integer.parseInt(page),Integer.parseInt(limit), Sort.by(orderBy));
        }else{
            pageable = PageRequest.of(Integer.parseInt(page),Integer.parseInt(limit), Sort.by(orderBy).descending());
        }
        if( active.equals("none")){
            bills = billRepository.findAllCustom(pageable,q);
        }else{
            bills = billRepository.findAllCustom(pageable,q,active);
        }
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

            List<BillChairDto> chairs = new ArrayList<>();
            List<BillDishDto> dishes = new ArrayList<>();

            List<BillDish> listDish = billDishRepository.getBillDishByBillDishId_Id(bill.getId());


            List<BillChair> listChair = billChairRepository.getBillChairByBillChairId_Id(bill.getId());

            //async
            CompletableFuture<ResponseEntity<Response>> responseRoomAsync = roomAsync.getRoomById(filmShowDto.getRoomId());
            CompletableFuture<ResponseEntity<Response>> responseSubFilmAsync = subFilmAsync.getSubFilmById(filmShowDto.getSubFilmId());
            CompletableFuture<Void> dishAsync = CompletableFuture.supplyAsync(()-> {
                System.out.println("Thread: " + Thread.currentThread().getName());
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
                return null;
            },executor);
            CompletableFuture<Void> chairAsync = CompletableFuture.supplyAsync(()-> {
                System.out.println("Thread: " + Thread.currentThread().getName());
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
                return null;
            },executor);

            CompletableFuture.allOf(responseRoomAsync,responseSubFilmAsync,dishAsync);

            ResponseEntity<Response> responseRoom = responseRoomAsync.join();
            ResponseEntity<Response> responseSubFilm = responseSubFilmAsync.join();

            //call room
//            ResponseEntity<Response> responseRoom = roomFeign.getRoomById(filmShowDto.getRoomId());
            //call film
//            ResponseEntity<Response> responseSubFilm = subFilmFeign.getSubFilmById(filmShowDto.getSubFilmId());


            if (responseRoom.getStatusCode() != HttpStatus.OK) {
                log.error("Room not found");
                throw new RoomNotFound("Room not found");
            }
            RoomDto roomDto = objectMapper.convertValue(responseRoom.getBody().getData(), RoomDto.class);
            if (responseSubFilm.getStatusCode() != HttpStatus.OK) {
                log.error("Film not found");
                throw new FilmNotFound("Film not found");
            }
            SubFilmDto subFilmDto = objectMapper.convertValue(responseSubFilm.getBody().getData(), SubFilmDto.class);


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
                    .nameBranch(roomDto.getBranch().getNameBranch())
                    .address(roomDto.getBranch().getAddress())
                    .roomId(roomDto.getId())
                    .nameRoom(roomDto.getName())
                    .filmId(subFilmDto.getFilmDto().getId())
                    .nameFilm(subFilmDto.getFilmDto().getName())
                    .userName(bill.getUserName())
                    .email(bill.getEmail())
                    .numberPhone(bill.getNumberPhone())
                    .chairs(chairs)
                    .dishes(dishes)
                    .qrCode(bill.getQrcode())
                    .build();
            list.add(billDto);
        });
        return PageObject.builder()
                .data(list)
                .pageCurrent(Integer.parseInt(page)+1)
                .totalPages(bills.getTotalPages())
                .build();
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
        ResponseEntity<Response> responseSubFilm = subFilmFeign.getSubFilmById(filmShowDto.getSubFilmId());
        if (responseSubFilm.getStatusCode() != HttpStatus.OK) {
            log.error("Film not found");
            throw new FilmNotFound("Film not found");
        }
        SubFilmDto subFilmDto = objectMapper.convertValue(responseSubFilm.getBody().getData(), SubFilmDto.class);

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
                .nameBranch(roomDto.getBranch().getNameBranch())
                .address(roomDto.getBranch().getAddress())
                .roomId(roomDto.getId())
                .nameRoom(roomDto.getName())
                .filmId(subFilmDto.getFilmDto().getId())
                .nameFilm(subFilmDto.getFilmDto().getName())
                .chairs(chairs)
                .dishes(dishes)
                .userName(bill.getUserName())
                .email(bill.getEmail())
                .numberPhone(bill.getNumberPhone())
                .qrCode(bill.getQrcode())
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
        billOptional.get().setActive(Active.DELETE);
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

    @Override
    public BillDto acceptBill(String id, String transaction) {
        Optional<Bill> billOptional = billRepository.findById(id);
        if( billOptional.isEmpty() ){
            throw new BillNotFound("Bill not found with id : " + id);
        }
        Bill bill = billOptional.get();
        bill.setTransactionCode(transaction);
        bill.setStatus(Status.SUCCESS);
        try{
            Bill billSaved = billRepository.save(bill);
            billRepository.flush();

            BillDto billConvert = getBillById(id);
            log.info("Create bill dto...");
            //send email
            TicketEmail ticketEmail = new TicketEmail(
                    billConvert.getTotalPrice(),
                    billConvert.getTransactionCode(),
                    billConvert.getPaymentMethodId(),
                    billConvert.getPaymentMethod(),
                    new ArrayList<>(),
                    new ArrayList<>(),
                    billConvert.getNameBranch(),
                    billConvert.getAddress(),
                    billConvert.getTimestamp(),
                    billConvert.getTimeEnd(),
                    billConvert.getTimeStart(),
                    billConvert.getTimeStampSee(),
                    billConvert.getNameRoom(),
                    billConvert.getNameFilm(),
                    billConvert.getUserName(),
                    billConvert.getEmail(),
                    billConvert.getNumberPhone(),
                    billConvert.getQrCode()
            );
            if( billConvert.getChairs() != null){
                billConvert.getChairs().forEach( item -> {
                    ticketEmail.getChairs().add(
                            new BillChairTicket(
                                    item.getChairCode(),
                                    item.getPrice(),
                                    item.getTicket().getConditionUse(),
                                    item.getTicket().getName(),
                                    item.getTicket().getTypeTicket()
                            )
                    );
                });
            }
            if( billConvert.getDishes() != null ){
                billConvert.getDishes().forEach( item -> {
                    ticketEmail.getDishes().add(
                            new BillDishTicket(
                                    item.getPrice(),
                                    item.getAmount(),
                                    item.getActive().toString(),
                                    item.getDishDto().getName(),
                                    item.getDishDto().getImage(),
                                    item.getDishDto().getTypeDish().getName()
                            )
                    );
                });
            }
            log.info("Send kafka...");
            kafkaMessagePublish.sendEventToTopic(
                    ticketEmail
            );
            return billConvert;
        }catch ( Exception e ){
            log.error("Bill save failed : " + e.getMessage());
            throw new ErrorException("Can't accept bill");
        }
    }

    @Override
    public List<BillDto> getAllBillByFilmShow(Integer filmShow) {
        List<Bill> bills = billRepository.findByFilmShowTimeId(filmShow);
        List<BillDto> list = new ArrayList<>();
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
            ResponseEntity<Response> responseSubFilm = subFilmFeign.getSubFilmById(filmShowDto.getSubFilmId());
            if (responseSubFilm.getStatusCode() != HttpStatus.OK) {
                log.error("Film not found");
                throw new FilmNotFound("Film not found");
            }
            SubFilmDto subFilmDto = objectMapper.convertValue(responseSubFilm.getBody().getData(), SubFilmDto.class);

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
                    .nameBranch(roomDto.getBranch().getNameBranch())
                    .address(roomDto.getBranch().getAddress())
                    .roomId(roomDto.getId())
                    .nameRoom(roomDto.getName())
                    .filmId(subFilmDto.getFilmDto().getId())
                    .nameFilm(subFilmDto.getFilmDto().getName())
                    .userName(bill.getUserName())
                    .email(bill.getEmail())
                    .numberPhone(bill.getNumberPhone())
                    .chairs(chairs)
                    .dishes(dishes)
                    .qrCode(bill.getQrcode())
                    .build();
            list.add(billDto);
        });
        return list;
    }
}
