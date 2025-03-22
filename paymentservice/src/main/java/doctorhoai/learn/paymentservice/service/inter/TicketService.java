package doctorhoai.learn.paymentservice.service.inter;

import doctorhoai.learn.paymentservice.dto.TicketDto;
import doctorhoai.learn.paymentservice.entity.Ticket;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TicketService {
    TicketDto addTicket(TicketDto ticketDto);
    TicketDto updateTicket(String id,TicketDto ticketDto);
    TicketDto getTicket(String id);
    Page<TicketDto> getTickets(String limit, String page, String active, String orderBy, String asc, String q);
    void deleteTicket(String id);
    void activeTicket(String id);
    List<TicketDto> getTicketByActive();
}
