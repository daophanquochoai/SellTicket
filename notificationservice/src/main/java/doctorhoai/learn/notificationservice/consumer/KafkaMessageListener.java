package doctorhoai.learn.notificationservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import doctorhoai.learn.basedomain.Event.TicketEmail;
import doctorhoai.learn.notificationservice.service.inter.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageListener {
    private final ObjectMapper objectMapper;
    private final MailService mailService;

    @RetryableTopic(attempts = "4", backoff = @Backoff( delay = 3000, multiplier = 1.5, maxDelay = 15000))
    @KafkaListener(topics = "ticket", groupId = "ticket")
    public void consumerTicket(TicketEmail ticketEmail) throws Exception {
        mailService.sendMail(ticketEmail);
        log.info("Mail sended");
    }

    @DltHandler
    public void listenDLT(TicketEmail ticketEmail) {
        System.out.println(ticketEmail.toString());
    }
}
