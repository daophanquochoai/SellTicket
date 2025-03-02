package doctorhoai.learn.notificationservice.service.inter;

import doctorhoai.learn.basedomain.Event.MailOpt;
import doctorhoai.learn.basedomain.Event.TicketEmail;

public interface MailService {
    void sendMail(TicketEmail ticketEmail);
    void sendOptMail(MailOpt mailOpt);
}
