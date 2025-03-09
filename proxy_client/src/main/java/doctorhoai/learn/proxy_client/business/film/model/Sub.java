package doctorhoai.learn.proxy_client.business.film.model;

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
    @OneToMany(fetch = FetchType.LAZY)
    private List<SubFilm> subFilms;
}
