package doctorhoai.learn.proxy_client.business.time.model.request;


import doctorhoai.learn.proxy_client.business.time.Validation.ValidTimeRange;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ValidTimeRange
public class FilmShowRequest {
    private int id;
    private Time timeEnd;
    private Time timeStart;
    private LocalDate timestamp;
    private String status;
    @NotNull
    private String filmId;
    @NotNull
    private String roomId;
}
