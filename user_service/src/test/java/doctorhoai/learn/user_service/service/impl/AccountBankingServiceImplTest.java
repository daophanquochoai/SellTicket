package doctorhoai.learn.user_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class AccountBankingServiceImplTest {
    @InjectMocks
    private AccountBankingServiceImpl accountBankService;

    @Test
    public void randomOpt(){
        System.out.println(accountBankService.randomOpt());
    }
}