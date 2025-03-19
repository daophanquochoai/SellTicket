package doctorhoai.learn.rateservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageObject {
    private Integer pageCurrent;
    private Integer totalPage;
    private Object data;
}
