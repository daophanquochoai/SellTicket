package doctorhoai.learn.user_service.entity;

import jakarta.persistence.*;

@Entity
public class Employee {
    @Id
    private String id;
    private String name;
    private String CCCD;
    private String email;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Account account;
}
