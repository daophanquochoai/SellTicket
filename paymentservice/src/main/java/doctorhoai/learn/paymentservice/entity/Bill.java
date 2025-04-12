package doctorhoai.learn.paymentservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
    @OneToMany(mappedBy = "billChairId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BillChair> billChair;
    @OneToMany(mappedBy = "billDishId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BillDish> billDish;
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
    private String qrcode;
    private String customerId;

    @Override
    public String toString() {
        return "Bill{" +
                "id='" + id + '\'' +
                ", totalPrice=" + totalPrice +
                ", transactionCode='" + transactionCode + '\'' +
                ", filmShowTimeId=" + filmShowTimeId +
                ", paymentMethodId=" + paymentMethodId +
                ", active=" + active +
                ", timestamp=" + timestamp +
                ", status=" + status +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", numberPhone='" + numberPhone + '\'' +
                '}';
    }
}
