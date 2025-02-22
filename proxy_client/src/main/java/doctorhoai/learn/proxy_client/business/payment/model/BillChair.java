package doctorhoai.learn.proxy_client.business.payment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillChair {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String chairCode;
    private Float price;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private Ticket ticketId;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_chair_id")
    private Bill billChairId;
    @Enumerated(EnumType.STRING)
    private Active active;
}
