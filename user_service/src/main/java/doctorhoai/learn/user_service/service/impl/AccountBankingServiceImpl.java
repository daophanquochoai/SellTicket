package doctorhoai.learn.user_service.service.impl;

import doctorhoai.learn.basedomain.Event.MailOpt;
import doctorhoai.learn.user_service.entity.Active;
import doctorhoai.learn.user_service.entity.Customer;
import doctorhoai.learn.user_service.entity.Employee;
import doctorhoai.learn.user_service.exception.AccountBankNotFound;
import doctorhoai.learn.user_service.exception.CustomerNotFound;
import doctorhoai.learn.user_service.exception.EmployeeNotFound;
import doctorhoai.learn.user_service.exception.ErrorException;
import doctorhoai.learn.user_service.helper.MapperToDto;
import doctorhoai.learn.user_service.repository.CustomerRepository;
import doctorhoai.learn.user_service.repository.EmployeeRepository;
import doctorhoai.learn.user_service.service.inter.AccountBankService;
import doctorhoai.learn.user_service.service.producer.KafkaMessagePublish;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountBankingServiceImpl implements AccountBankService {

    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final KafkaMessagePublish kafkaMessagePublish;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void forgetAccountAdmin(String email) {
        Optional<Employee> employee = employeeRepository.findByEmail(email);
        if( employee.isEmpty() ){
            throw new EmployeeNotFound("Employee not found with email : " + email);
        }
        String opt = randomOpt();
        try{
            redisTemplate.delete(employee.get().getEmail());
            redisTemplate.opsForValue().set(employee.get().getEmail(), opt, Duration.ofMinutes(2));
            kafkaMessagePublish.sendOpt(new MailOpt(email, opt));
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @Transactional
    @Override
    public void forgetAccountUser(String email) {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        if( customer.isEmpty() ){
            throw new CustomerNotFound("Customer not found with email : " + email);
        }
        String opt = randomOpt();
        try{
            redisTemplate.delete(customer.get().getEmail());
            redisTemplate.opsForValue().set(customer.get().getEmail(), opt, Duration.ofMinutes(2));
            kafkaMessagePublish.sendOpt(new MailOpt(email, opt));
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @Override
    public void changePasswordCustomer(String password, String opt, String email) {
        try{
            String optSaved = redisTemplate.opsForValue().get(email);
            if( optSaved.equals(opt) ){
                Optional<Customer> customer = customerRepository.findByEmail(email);
                if( customer.isEmpty() ){
                    throw new CustomerNotFound("Customer not found with email : " + email);
                }
                customer.get().getAccount().setPassword(bCryptPasswordEncoder.encode(password));
                customerRepository.save(customer.get());
            }
        }catch (Exception ex){
            log.error("AccountBank : 85 : " + ex.getMessage());
            throw new ErrorException("Process fail");
        }
    }

    @Override
    public void changePasswordAdmin(String password, String opt, String email) {
        try{
            String optSaved = redisTemplate.opsForValue().get(email);
            if( optSaved.equals(opt) ){
                Optional<Employee> employee = employeeRepository.findByEmail(email);
                if( employee.isEmpty() ){
                    throw new CustomerNotFound("Customer not found with email : " + email);
                }
                employee.get().getAccount().setPassword(bCryptPasswordEncoder.encode(password));
                employeeRepository.save(employee.get());
            }
        }catch (Exception ex){
            log.error("AccountBank : 103 : " + ex.getMessage());
            throw new ErrorException("Process fail");
        }
    }


    //ham random 4 so
    public String randomOpt(){
        String container = "0123456789";
        Random random = new Random();
        String returnValue = "";
        for( int i = 0 ; i < 6 ; i++){
            int randomIndex = random.nextInt(10);
            returnValue += container.charAt(randomIndex);
        }
        return returnValue;
    }
}
