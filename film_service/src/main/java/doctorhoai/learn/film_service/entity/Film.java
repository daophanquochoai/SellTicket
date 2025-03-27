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
@Builder
@Data
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private int age;
    private String image;
    private String description;
    private String content;
    private String trailer;
    private String nation;
    private String duration;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Film_Type",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "type_film_id")
    )
    private List<TypeFilm> typeFilms;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "filmId")
    private List<SubFilm> subFilms;
}