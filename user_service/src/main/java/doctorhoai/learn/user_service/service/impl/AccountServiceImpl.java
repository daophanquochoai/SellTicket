package doctorhoai.learn.user_service.service.impl;

import doctorhoai.learn.user_service.dto.response.ReportAccount;
import doctorhoai.learn.user_service.repository.ReportNumberRepository;
import doctorhoai.learn.user_service.service.inter.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final ReportNumberRepository repository;

    @Transactional
    @Override
    public ReportAccount getNumCustomerAndEmployee() {

        ReportAccount object = repository.getNumCustomerAndEmployee();
        return object;
    }
}
