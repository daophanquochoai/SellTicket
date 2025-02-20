package doctorhoai.learn.user_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountBanking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String ccv;
    @Enumerated(EnumType.STRING)
    private Active active;
    private String cardCode;
    private Date dayStart;
    private Date dayEnd;
    private String name;
}
