package doctorhoai.learn.user_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountBanking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String cvv;
    @Enumerated(EnumType.STRING)
    private Active active;
    private String cardCode;
    private Date dayStart;
    private Date dayEnd;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Customer customerId;
}
