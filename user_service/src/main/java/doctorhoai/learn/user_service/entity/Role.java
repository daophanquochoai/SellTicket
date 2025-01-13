package doctorhoai.learn.user_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String roleName;
    @Enumerated(EnumType.STRING)
    private Status status;
    public Role(String roleName, Status status) {
        this.roleName = roleName;
        this.status = status;
    }
}
