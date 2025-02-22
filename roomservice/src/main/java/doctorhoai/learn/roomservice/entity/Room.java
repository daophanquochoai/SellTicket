package doctorhoai.learn.roomservice.entity;

import doctorhoai.learn.roomservice.helper.PositionChairConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

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
    @Convert(converter =  PositionChairConverter.class)
    private Integer[][] positionChair;
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
    @Enumerated(EnumType.STRING)
    private Status status;
}
