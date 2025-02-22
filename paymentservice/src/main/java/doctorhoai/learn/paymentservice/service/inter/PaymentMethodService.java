package doctorhoai.learn.paymentservice.service.inter;

import doctorhoai.learn.paymentservice.dto.PaymentMethodDto;

import java.util.List;

public interface PaymentMethodService {
    PaymentMethodDto addPaymentMethod(PaymentMethodDto paymentMethodDto);
    PaymentMethodDto updatePaymentMethod(String id, PaymentMethodDto paymentMethodDto);
    PaymentMethodDto getPaymentMethod(String id);
    List<PaymentMethodDto> getPaymentMethods();
    void deletePaymentMethod(String id);
    void activePaymentMethod(String id);
    List<PaymentMethodDto> getActivePaymentMethods();
}
