package doctorhoai.learn.proxy_client.business.film.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
}
