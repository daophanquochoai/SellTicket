package doctorhoai.learn.user_service.entity;

import jakarta.persistence.*;

@Entity
public class Account {
    @Id
    private String userName;
    private String password;
    @OneToOne(cascade = CascadeType.ALL, targetEntity = Role.class, fetch = FetchType.LAZY)
    private Role role;
    @Enumerated(EnumType.STRING)
    private Status active;
}
