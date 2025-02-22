package doctorhoai.learn.paymentservice.service.impl;

import doctorhoai.learn.paymentservice.dto.TicketDto;
import doctorhoai.learn.paymentservice.entity.Active;
import doctorhoai.learn.paymentservice.entity.Ticket;
import doctorhoai.learn.paymentservice.exception.ErrorException;
import doctorhoai.learn.paymentservice.exception.TicketNotFound;
import doctorhoai.learn.paymentservice.helper.MapperToObject;
import doctorhoai.learn.paymentservice.repository.TicketRepository;
import doctorhoai.learn.paymentservice.service.inter.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final MapperToObject mapperToObject;

    @Override
    public TicketDto addTicket(TicketDto ticketDto){
        log.info("** TicketServiceImpl, Add ticket **");
        Ticket ticket = mapperToObject.mapperToTicket(ticketDto);
        try{
            Ticket tickedSaved = ticketRepository.save(ticket);
            return mapperToObject.mapperToTicketDto(ticket);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public TicketDto updateTicket(String id, TicketDto ticketDto) {
        log.info("** TicketServiceImpl, Update ticket **");
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if( ticket.isEmpty()){
            throw new TicketNotFound("Ticket not found with id : " + id);
        }
        Ticket ticketSaved = ticket.get();
        ticketSaved.setTypeTicket(ticketDto.getTypeTicket());
        ticketSaved.setActive(ticketDto.getActive());
        ticketSaved.setName(ticketDto.getName());
        ticketSaved.setPrice(ticketDto.getPrice());
        ticketSaved.setConditionUse(ticketDto.getConditionUse());
        try{
            Ticket ticketUpdated = ticketRepository.save(ticketSaved);
            return mapperToObject.mapperToTicketDto(ticketSaved);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public TicketDto getTicket(String id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if( ticket.isEmpty()){
            throw new TicketNotFound("Ticket not found with id : " + id);
        }
        Ticket ticketSaved = ticket.get();
        return mapperToObject.mapperToTicketDto(ticketSaved);
    }

    @Override
    public List<TicketDto> getTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream().map(mapperToObject::mapperToTicketDto).toList();
    }

    @Override
    public void deleteTicket(String id) {
        Optional<Ticket> ticketSaved = ticketRepository.findById(id);
        if( ticketSaved.isEmpty()){
            throw new TicketNotFound("Ticket not found with id : " + id);
        }
        ticketSaved.get().setActive(Active.INACTIVE);
        try{
            Ticket ticketUpdated = ticketRepository.save(ticketSaved.get());
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public void activeTicket(String id) {
        Optional<Ticket> ticketSaved = ticketRepository.findById(id);
        if( ticketSaved.isEmpty()){
            throw new TicketNotFound("Ticket not found with id : " + id);
        }
        ticketSaved.get().setActive(Active.ACTIVE);
        try{
            Ticket ticketUpdated = ticketRepository.save(ticketSaved.get());
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }
}
