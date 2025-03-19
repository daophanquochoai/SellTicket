package doctorhoai.learn.film_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Sub {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column( unique = true)
    private String sub;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "subId")
    private List<SubFilm> subFilms;
}
