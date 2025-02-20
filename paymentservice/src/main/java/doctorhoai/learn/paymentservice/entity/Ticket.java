package doctorhoai.learn.paymentservice.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Active active;
    private String conditionUse;
    private String name;
    private String price;
    private String typeTicket;
}
