package doctorhoai.learn.user_service.dto.request;

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
    private String passwordOld;
    private String passwordNew;
}
