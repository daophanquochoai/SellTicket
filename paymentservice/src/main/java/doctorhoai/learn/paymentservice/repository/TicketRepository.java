package doctorhoai.learn.paymentservice.repository;

import doctorhoai.learn.paymentservice.entity.Active;
import doctorhoai.learn.paymentservice.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {
    Page<Ticket> findAll(Pageable pageable);
    Page<Ticket> findAllByActive(Pageable pageable , Active active);
}
