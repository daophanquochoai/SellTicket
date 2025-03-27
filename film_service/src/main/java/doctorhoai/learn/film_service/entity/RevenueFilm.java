package doctorhoai.learn.film_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQuery;
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
        resultClasses = RevenueFilm.class
)
@NamedStoredProcedureQuery(
        name="revenueAllInFilm",
        procedureName = "revenueAllInFilm",
        resultClasses = RevenueFilm.class
)
public class RevenueFilm {
    @Id
    private String id;
    private String name;
    private Long total_revenue;
}
