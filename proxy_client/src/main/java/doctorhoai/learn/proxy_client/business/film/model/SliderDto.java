package doctorhoai.learn.proxy_client.business.film.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SliderDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    @NotBlank(message = "Hình ảnh không thể trống")
    private String image;
    @NotBlank(message = "Tên hình ảnh không thể trống")
    private String name;
}
