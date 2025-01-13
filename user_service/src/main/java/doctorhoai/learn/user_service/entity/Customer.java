package doctorhoai.learn.user_service.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Customer {
    @Id
    private String id;
    private String name;
    private String phoneNumber;
    private String email;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Account account;
    private Date timestamp;
}
