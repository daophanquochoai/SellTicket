package doctorhoai.learn.paymentservice.service.impl;

import doctorhoai.learn.paymentservice.dto.PaymentMethodDto;
import doctorhoai.learn.paymentservice.entity.Active;
import doctorhoai.learn.paymentservice.entity.PaymentMethod;
import doctorhoai.learn.paymentservice.exception.ErrorException;
import doctorhoai.learn.paymentservice.repository.PaymentMethodRepository;
import doctorhoai.learn.paymentservice.service.inter.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;


    @Override
    public PaymentMethodDto addPaymentMethod(PaymentMethodDto paymentMethodDto) {
        PaymentMethod paymentMethod = PaymentMethod.builder()
                .active(paymentMethodDto.getActive())
                .method(paymentMethodDto.getMethod())
                .build();
        try{
            PaymentMethod paymentMethodSaved = paymentMethodRepository.save(paymentMethod);
            return PaymentMethodDto.builder()
                    .id(paymentMethodSaved.getId())
                    .active(paymentMethodSaved.getActive())
                    .method(paymentMethodSaved.getMethod())
                    .build();
        }catch (Exception e){
            throw new ErrorException("Service is error");
        }
    }

    @Override
    public PaymentMethodDto updatePaymentMethod(String id, PaymentMethodDto paymentMethodDto) {
        Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepository.findById(id);
        if( paymentMethodOptional.isEmpty() ){
            throw new ErrorException("Service is error");
        }
        paymentMethodOptional.get().setMethod(paymentMethodDto.getMethod());
        paymentMethodOptional.get().setActive(paymentMethodDto.getActive());
        try{
            PaymentMethod paymentMethodUpdated = paymentMethodRepository.save(paymentMethodOptional.get());
            return PaymentMethodDto.builder()
                    .id(paymentMethodUpdated.getId())
                    .active(paymentMethodUpdated.getActive())
                    .method(paymentMethodUpdated.getMethod())
                    .build();
        }catch (Exception e){
            throw new ErrorException("Service is error");
        }
    }

    @Override
    public PaymentMethodDto getPaymentMethod(String id) {
        Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepository.findById(id);
        if( paymentMethodOptional.isEmpty() ){
            throw new ErrorException("Service is error");
        }
        PaymentMethod paymentMethod = paymentMethodOptional.get();
        return PaymentMethodDto.builder()
                .id(paymentMethod.getId())
                .active(paymentMethod.getActive())
                .method(paymentMethod.getMethod())
                .build();
    }


    @Override
    public List<PaymentMethodDto> getPaymentMethods() {
        List<PaymentMethod> list = paymentMethodRepository.findAll();
        return list.stream().map(item ->{
            return PaymentMethodDto.builder()
                    .id(item.getId())
                    .active(item.getActive())
                    .method(item.getMethod())
                    .build();
        }).toList();
    }

    @Override
    public void deletePaymentMethod(String id) {
        Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepository.findById(id);
        if( paymentMethodOptional.isEmpty() ){
            throw new ErrorException("Service is error");
        }
        paymentMethodOptional.get().setActive(Active.INACTIVE);
        try{
            PaymentMethod paymentMethodUpdated = paymentMethodRepository.save(paymentMethodOptional.get());
        }catch (Exception e){
            throw new ErrorException("Service is error");
        }
    }

    @Override
    public void activePaymentMethod(String id) {
        Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepository.findById(id);
        if( paymentMethodOptional.isEmpty() ){
            throw new ErrorException("Service is error");
        }
        paymentMethodOptional.get().setActive(Active.ACTIVE);
        try{
            PaymentMethod paymentMethodUpdated = paymentMethodRepository.save(paymentMethodOptional.get());
        }catch (Exception e){
            throw new ErrorException("Service is error");
        }
    }

    @Override
    public List<PaymentMethodDto> getActivePaymentMethods() {
        List<PaymentMethod> list = paymentMethodRepository.getPaymentMethodByActive(Active.ACTIVE);
        return list.stream().map(item ->{
            return PaymentMethodDto.builder()
                    .id(item.getId())
                    .active(item.getActive())
                    .method(item.getMethod())
                    .build();
        }).toList();
    }
}
