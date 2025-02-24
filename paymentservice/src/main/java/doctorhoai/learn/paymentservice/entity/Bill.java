package doctorhoai.learn.paymentservice.entity;

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
    @Column(nullable = false)
    private Float totalPrice;
    @Column(unique = true, nullable = false)
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
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String numberPhone;
}
