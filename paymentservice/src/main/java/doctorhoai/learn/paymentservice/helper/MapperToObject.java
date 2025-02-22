package doctorhoai.learn.paymentservice.helper;

import doctorhoai.learn.paymentservice.dto.BillDto;
import doctorhoai.learn.paymentservice.dto.TicketDto;
import doctorhoai.learn.paymentservice.entity.Bill;
import doctorhoai.learn.paymentservice.entity.Ticket;
import org.springframework.stereotype.Service;

@Service
public class MapperToObject {

    public Ticket mapperToTicket(TicketDto ticketDto) {
        return Ticket
                .builder()
                .id(ticketDto.getId())
                .active(ticketDto.getActive())
                .conditionUse(ticketDto.getConditionUse())
                .name(ticketDto.getName())
                .price(ticketDto.getPrice())
                .typeTicket(ticketDto.getTypeTicket())
                .build();
    }

    public TicketDto mapperToTicketDto(Ticket ticket) {
        return TicketDto
                .builder()
                .id(ticket.getId())
                .active(ticket.getActive())
                .conditionUse(ticket.getConditionUse())
                .name(ticket.getName())
                .price(ticket.getPrice())
                .typeTicket(ticket.getTypeTicket())
                .build();
    }


}
