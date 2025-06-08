package doctorhoai.learn.film_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PageResponse {
    private String page;
    private String totalPage;
    private Object data;
}
