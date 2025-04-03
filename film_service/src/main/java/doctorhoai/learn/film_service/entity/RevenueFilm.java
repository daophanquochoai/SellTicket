package doctorhoai.learn.film_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@NamedStoredProcedureQuery(
        name="revenueInFilm",
        procedureName = "revenueInFilm",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "monthNow", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "yearNow", type = Integer.class),
        },
        resultClasses = RevenueFilm.class
)
@NamedStoredProcedureQuery(
        name="revenueInFilmByFilmId",
        procedureName = "revenueInFilmByFilmId",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "filmId", type = String.class),
        },
        resultClasses = RevenueFilm.class
)
public class RevenueFilm {
    @Id
    private String id;
    private String name;
    private Long total_revenue;
}
