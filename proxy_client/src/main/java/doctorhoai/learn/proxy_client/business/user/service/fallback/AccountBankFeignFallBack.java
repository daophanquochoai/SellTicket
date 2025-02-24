package doctorhoai.learn.proxy_client.business.user.service.fallback;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.user.model.AccountBankingDto;
import doctorhoai.learn.proxy_client.business.user.service.AccountBankFeign;
import doctorhoai.learn.proxy_client.security.FunctionCommon;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountBankFeignFallBack implements FallbackFactory<AccountBankFeign> {

    private final FunctionCommon functionCommon;
    @Override
    public AccountBankFeign create(Throwable cause) {
        return new AccountBankFeign() {
            @Override
            public ResponseEntity<Response> addAccountBank(AccountBankingDto accountBankingDto) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> updateAccountBank(String id, AccountBankingDto accountBankingDto) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> deleteAccountBank(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> activateAccountBank(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> getAccountBankByCustomerId(String customerId) {
                return functionCommon.process(cause);
            }
        };
    }
}
