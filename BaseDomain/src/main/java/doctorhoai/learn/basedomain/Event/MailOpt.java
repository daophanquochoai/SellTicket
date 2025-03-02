package doctorhoai.learn.basedomain.Event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MailOpt implements Serializable {
    private static final long serialVersionUID = 1L;
    private String toEmail;
    private String opt;
}
