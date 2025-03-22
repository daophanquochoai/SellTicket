package doctorhoai.learn.paymentservice.repository;

import doctorhoai.learn.paymentservice.entity.Active;
import doctorhoai.learn.paymentservice.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {
    @Query("select t from Ticket t where t.name like concat('%', :q, '%') or t.conditionUse like concat('%', :q, '%')")
    Page<Ticket> getTicketByCustom(Pageable pageable, String q);
    @Query("select t from Ticket t where t.active = :status and ( t.name like concat('%', :q, '%') or t.conditionUse like concat('%', :q, '%'))")
    Page<Ticket> getTicketByCustom(Pageable pageable , String q, Active active);
    List<Ticket> getTicketByActive(Active active);
}
