package doctorhoai.learn.user_service.service.inter;

import java.util.List;

public interface AccountBankService {
    void forgetAccountAdmin( String email );
    void forgetAccountUser(String email);
    void changePasswordCustomer( String password, String opt, String email);
    void changePasswordAdmin( String password, String op, String email);
}
