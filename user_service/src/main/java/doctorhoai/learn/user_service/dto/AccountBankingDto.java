package doctorhoai.learn.user_service.dto;

import doctorhoai.learn.user_service.entity.Active;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountBankingDto {
    private String id;
    @Length(min = 3, max = 4, message = "CCV has 3-4 digits")
    private String cvv;
    private Active active;
    @NotNull(message = "Card code can't not null")
    @NotBlank(message = "Card code can't not blank")
    private String cardCode;
    @NotNull(message = "The start date can't null")
    @PastOrPresent(message = "The start date is the current date or is in the past")
    private Date dayStart;
    @NotNull(message = "The end date can't null")
    @FutureOrPresent(message = "The end date is in the future")
    private Date dayEnd;
    @NotNull(message = "Name can't null")
    @NotBlank(message = "Name can't blank")
    private String name;
}
