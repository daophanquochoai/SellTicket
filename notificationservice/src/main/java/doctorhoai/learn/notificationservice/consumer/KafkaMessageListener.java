package doctorhoai.learn.notificationservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import doctorhoai.learn.basedomain.Event.MailOpt;
import doctorhoai.learn.basedomain.Event.TicketEmail;
import doctorhoai.learn.notificationservice.controller.Controller;
import doctorhoai.learn.notificationservice.service.inter.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageListener {
    private final ObjectMapper objectMapper;
    private final MailService mailService;
    private final Controller controller;

    @RetryableTopic(attempts = "4", backoff = @Backoff( delay = 3000, multiplier = 1.5, maxDelay = 15000))
    @KafkaListener(topics = "ticket", groupId = "ticket")
    public void consumerTicket(TicketEmail ticketEmail) throws Exception {
        mailService.sendMail(ticketEmail);
        controller.sendSSE(ticketEmail.getChairs());
        log.info("Mail sended");
    }

    @DltHandler
    public void listenDLT(TicketEmail ticketEmail) {
        System.out.println(ticketEmail.toString());
    }
    @RetryableTopic( attempts = "4", backoff = @Backoff(delay = 3000, multiplier = 1.5, maxDelay = 15000))
    @KafkaListener(topics = "optMail", groupId = "opt")
    public void consumerOpt(MailOpt mailOpt) throws Exception {
        mailService.sendOptMail(mailOpt);
        log.info("Mail has been to mail opt");
    }
}
