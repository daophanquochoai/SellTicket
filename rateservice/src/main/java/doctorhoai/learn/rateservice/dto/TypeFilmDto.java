package doctorhoai.learn.rateservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TypeFilmDto {
    private String id;
    @NotBlank(message = "Type Film hasn't blank")
    private String name;
    @Pattern(regexp = "^(ACTIVE|DELETE)$", message = "Active should value 'ACTIVE' or 'DELETE'")
    private String active;
}

