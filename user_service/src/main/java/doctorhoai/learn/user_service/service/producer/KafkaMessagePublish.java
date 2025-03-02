package doctorhoai.learn.user_service.service.producer;

import doctorhoai.learn.basedomain.Event.MailOpt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Slf4j
@Service
public class KafkaMessagePublish {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${spring.kafka.topic.name}")
    private String optMail;

    public void sendOpt(MailOpt mailOpt){
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(optMail, mailOpt);
        future.whenComplete( (result, throwable) -> {
            if( throwable == null){
                log.info("Send message = [{}] with offset = [{}], partitions = [{}]", mailOpt.getOpt(), result.getRecordMetadata().offset(), result.getRecordMetadata().partition());
            }else{
                log.error("Unable to send message = [ {} ] due to : {}", mailOpt.getOpt() , throwable.getMessage());
            }
        });
    }
}
