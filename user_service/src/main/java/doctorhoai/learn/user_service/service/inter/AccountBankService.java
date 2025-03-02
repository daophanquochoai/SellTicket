package doctorhoai.learn.user_service.service.inter;

import doctorhoai.learn.user_service.dto.AccountBankingDto;

import java.util.List;

public interface AccountBankService {
    AccountBankingDto addAccountBanking(AccountBankingDto accountBankingDto);
    AccountBankingDto updateAccountBanking(String id, AccountBankingDto accountBankingDto);
    void deleteAccountBanking(String id);
    void activeAccountBanking(String id);
    List<AccountBankingDto> getAccountBanking(String customerId);
    void forgetAccountAdmin( String email );
    void forgetAccountUser(String email);
    void changePasswordCustomer( String password, String opt, String email);
    void changePasswordAdmin( String password, String op, String email);
}
