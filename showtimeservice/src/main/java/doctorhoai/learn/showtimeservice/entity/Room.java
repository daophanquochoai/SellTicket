package doctorhoai.learn.showtimeservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique=true)
    private String name;
    @Lob
    @Column(nullable = false)
    private String positionChair;
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
    @Enumerated(EnumType.STRING)
    private Status status;
}
