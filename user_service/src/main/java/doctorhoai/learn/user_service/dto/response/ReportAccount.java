package doctorhoai.learn.user_service.dto.response;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@NamedStoredProcedureQuery(
        name = "getMonthlyTotalPrice",
        procedureName = "HuongDichVu.getMonthlyTotalPrice",
        resultClasses = ReportAccount.class
)
public class ReportAccount {
    @Id
    private Long numCustomer;
    private Long numEmployee;
    private Long numBranch;
}
