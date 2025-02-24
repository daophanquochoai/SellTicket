package doctorhoai.learn.user_service.service.inter;

import doctorhoai.learn.user_service.dto.AccountBankingDto;

import java.util.List;

public interface AccountBankService {
    AccountBankingDto addAccountBanking(AccountBankingDto accountBankingDto);
    AccountBankingDto updateAccountBanking(String id, AccountBankingDto accountBankingDto);
    void deleteAccountBanking(String id);
    void activeAccountBanking(String id);
    List<AccountBankingDto> getAccountBanking(String customerId);
}
