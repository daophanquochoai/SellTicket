package doctorhoai.learn.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageObject {
    private Integer pageCurrent;
    private Integer totalPages;
    private Object data;

}
