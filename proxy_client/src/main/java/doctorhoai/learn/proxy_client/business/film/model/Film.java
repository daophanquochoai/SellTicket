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
@Builder
@Data
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private int age;
    private String image;
    private String nation;
    private String duration;
    private String description;
    private String content;
    private String trailer;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Film_Type",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "type_film_id")
    )
    private List<TypeFilm> typeFilms;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "filmId")
    private List<SubFilm> subFilms;
}