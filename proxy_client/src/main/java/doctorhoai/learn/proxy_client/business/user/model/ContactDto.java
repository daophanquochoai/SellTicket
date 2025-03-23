package doctorhoai.learn.proxy_client.business.user.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactDto {
    private Integer id;
    @NotBlank(message = "Tên không thể trống")
    @NotNull(message = "Tên không thể null")
    private String name;
    @Length(min = 10, max = 12, message = "Số điện thoại có 10-12 kí tự")
    private String numberPhone;
    @NotBlank(message = "Nội dung không thể trống")
    @NotNull(message = "Nội dung không thể trống")
    private String content;
    private String status;
    private LocalDateTime timestamp;
}
