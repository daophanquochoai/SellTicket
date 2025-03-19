package doctorhoai.learn.paymentservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@NamedStoredProcedureQuery(
        name = "getMonthlyTotalPrice",
        procedureName = "HuongDichVu.getMonthlyTotalPrice",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class)
        },
        resultClasses = MonthlyTotalPrice.class
)
@NamedStoredProcedureQuery(
        name = "getYearForBill",
        procedureName = "HuongDichVu.getYearForBill",
        resultClasses = String.class
)
@Data
public class MonthlyTotalPrice {

    @Id
    private Integer month;

    private Long totalPrice;

    // Getters & Setters
    public Integer getMonth() { return month; }
    public void setMonth(Integer month) { this.month = month; }

    public Long getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Long totalPrice) { this.totalPrice = totalPrice; }
}

