package doctorhoai.learn.paymentservice.service.inter;

import doctorhoai.learn.paymentservice.dto.TicketDto;

import java.util.List;

public interface TicketService {
    TicketDto addTicket(TicketDto ticketDto);
    TicketDto updateTicket(String id,TicketDto ticketDto);
    TicketDto getTicket(String id);
    List<TicketDto> getTickets();
    void deleteTicket(String id);
    void activeTicket(String id);
}
