package doctorhoai.learn.paymentservice.helper;

import doctorhoai.learn.paymentservice.dto.BillChairDto;
import doctorhoai.learn.paymentservice.dto.BillDto;
import doctorhoai.learn.paymentservice.dto.TicketDto;
import doctorhoai.learn.paymentservice.entity.Bill;
import doctorhoai.learn.paymentservice.entity.BillChair;
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
                .slot(ticketDto.getSlot())
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
                .slot(ticket.getSlot())
                .build();
    }
    public BillChairDto mapperToBillChairDto(BillChair billChair) {
        return BillChairDto.builder()
                .chairCode(billChair.getChairCode())
                .build();
    }

}
