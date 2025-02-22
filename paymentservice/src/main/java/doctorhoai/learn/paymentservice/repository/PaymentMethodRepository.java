package doctorhoai.learn.paymentservice.repository;

import doctorhoai.learn.paymentservice.entity.Active;
import doctorhoai.learn.paymentservice.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, String> {
    List<PaymentMethod> getPaymentMethodByActive(Active active);
}
