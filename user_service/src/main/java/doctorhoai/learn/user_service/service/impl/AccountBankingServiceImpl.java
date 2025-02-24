package doctorhoai.learn.user_service.service.impl;

import doctorhoai.learn.user_service.dto.AccountBankingDto;
import doctorhoai.learn.user_service.entity.AccountBanking;
import doctorhoai.learn.user_service.entity.Active;
import doctorhoai.learn.user_service.exception.AccountBankNotFound;
import doctorhoai.learn.user_service.exception.ErrorException;
import doctorhoai.learn.user_service.helper.MapperToDto;
import doctorhoai.learn.user_service.repository.AccountBankRepository;
import doctorhoai.learn.user_service.service.inter.AccountBankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountBankingServiceImpl implements AccountBankService {

    private final AccountBankRepository accountBankRepository;

    @Override
    @Transactional
    public AccountBankingDto addAccountBanking(AccountBankingDto accountBankingDto) {
        try {
            AccountBanking accountBanking = MapperToDto.DtoToAccountBanking(accountBankingDto);
            AccountBanking accountBankSaved = accountBankRepository.save(accountBanking);
            return MapperToDto.AccountBankToDto(accountBankSaved);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getCause().toString());
        }
    }

    @Override
    @Transactional
    public AccountBankingDto updateAccountBanking(String id, AccountBankingDto accountBankingDto) {
        Optional<AccountBanking> accountBankingOptional = accountBankRepository.findById(id);
        if( accountBankingOptional.isEmpty() ){
            throw new AccountBankNotFound("Account Bank not found with id : " + id);
        }
        AccountBanking accountBanking = accountBankingOptional.get();
        accountBanking.setCvv(accountBankingDto.getCvv());
        accountBanking.setActive(accountBankingDto.getActive());
        accountBanking.setCardCode(accountBankingDto.getCardCode());
        accountBanking.setDayEnd(accountBankingDto.getDayEnd());
        accountBanking.setDayStart(accountBankingDto.getDayStart());
        accountBanking.setName(accountBankingDto.getName());
        try{
            AccountBanking accountBankSaved = accountBankRepository.save(accountBanking);
            return MapperToDto.AccountBankToDto(accountBankSaved);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getCause().getMessage());
        }
    }

    @Override
    public void deleteAccountBanking(String id) {
        Optional<AccountBanking> accountBankingOptional = accountBankRepository.findById(id);
        if( accountBankingOptional.isEmpty() ){
            throw new AccountBankNotFound("Account Bank not found with id : " + id);
        }
        AccountBanking accountBanking = accountBankingOptional.get();
        accountBanking.setActive(Active.DELETE);
        try{
            accountBankRepository.save(accountBanking);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getCause().getMessage());
        }
    }

    @Override
    public void activeAccountBanking(String id) {
        Optional<AccountBanking> accountBankingOptional = accountBankRepository.findById(id);
        if( accountBankingOptional.isEmpty() ){
            throw new AccountBankNotFound("Account Bank not found with id : " + id);
        }
        AccountBanking accountBanking = accountBankingOptional.get();
        accountBanking.setActive(Active.ACTIVE);
        try{
            accountBankRepository.save(accountBanking);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getCause().getMessage());
        }
    }

    @Override
    public List<AccountBankingDto> getAccountBanking(String customerId) {
        return accountBankRepository.findByCustomerId_Id(customerId)
                .stream().map(MapperToDto::AccountBankToDto).toList();
    }
}
