package doctorhoai.learn.paymentservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class TypeDishDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String active;
    @NotBlank(message = "Name can't blank")
    private String name;
}
