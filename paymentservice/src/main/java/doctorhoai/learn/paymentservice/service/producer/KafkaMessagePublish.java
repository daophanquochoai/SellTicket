package doctorhoai.learn.paymentservice.service.producer;

import doctorhoai.learn.basedomain.Event.TicketEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
@Slf4j
public class KafkaMessagePublish {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${spring.kafka.topic.name}")
    private String emailTopic;

    public void sendEventToTopic( TicketEmail ticketEmail){
        CompletableFuture<SendResult<String,Object>> future = kafkaTemplate.send(emailTopic, ticketEmail);
        future.whenComplete( (result, throwable) -> {
            if( throwable == null){
                log.info("Send message = [{}] with offset = [{}], partitions = [{}]", ticketEmail.toString(), result.getRecordMetadata().offset(), result.getRecordMetadata().partition());
            }else{
                log.error("Unable to send message = [ {} ] due to : {}", ticketEmail.toString(), throwable.getMessage());
            }
        });
    }
}
