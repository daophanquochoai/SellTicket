package doctorhoai.learn.proxy_client.business.payment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Bill {
    @Id
    @GeneratedValue( strategy = GenerationType.UUID)
    private String id;
    private Float totalPrice;
    private String transactionCode;
    private Integer filmShowTimeId;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethodId;
    @Enumerated(EnumType.STRING)
    private Active active;
    private LocalDateTime timestamp;
    @Enumerated(EnumType.STRING)
    private Status status;
}
