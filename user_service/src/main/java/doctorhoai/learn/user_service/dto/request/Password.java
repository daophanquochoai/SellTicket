package doctorhoai.learn.user_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Password implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(min = 8, message = "Mật khẩu có 8 kí tự trở lên")
    @NotBlank(message = "Mật khẩu không thể trống")
    private String passwordNew;
}
