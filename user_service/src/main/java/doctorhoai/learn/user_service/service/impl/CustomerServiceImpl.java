package doctorhoai.learn.user_service.service.impl;

import doctorhoai.learn.user_service.dto.CustomerDto;
import doctorhoai.learn.user_service.dto.request.AccountCustomer;
import doctorhoai.learn.user_service.dto.request.CustomerRequest;
import doctorhoai.learn.user_service.entity.*;
import doctorhoai.learn.user_service.exception.*;
import doctorhoai.learn.user_service.helper.MapperToDto;
import doctorhoai.learn.user_service.repository.AccountRepository;
import doctorhoai.learn.user_service.repository.CustomerRepository;
import doctorhoai.learn.user_service.repository.RoleRepository;
import doctorhoai.learn.user_service.service.inter.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public CustomerDto addCustomer(CustomerRequest customer) {
        Optional<Role> role = roleRepository.findById(1);
        if( role.isEmpty()){
            throw new RoleNotFound("Role not found with id : " + customer.getRoleId());
        }
        Optional<Customer> customerOptional = customerRepository.findByEmailOrPhoneNumber(customer.getEmail(), customer.getPhoneNumber());
        if(customerOptional.isPresent()){
            throw new CustomerDuplicated("Email or Phone Number has been registered");
        }
        Optional<Account> accountOptional = accountRepository.findByUserName(customer.getUserName());
        if(accountOptional.isPresent()){
            throw new AccountDuplicated("Username has been registered");
        }
        try{
            Account account = Account.builder()
                    .userName(customer.getUserName())
                    .password(bCryptPasswordEncoder.encode(customer.getPassword()))
                    .role(role.get())
                    .active(Status.ACTIVE)
                    .build();
            Account accountSaved = accountRepository.save(account);
            Customer c = Customer.builder()
                    .name(customer.getName())
                    .phoneNumber(customer.getPhoneNumber())
                    .email(customer.getEmail())
                    .timestamp(LocalDate.now())
                    .account(accountSaved)
                    .status(Status.ACTIVE)
                    .build();
            Customer customerSaved = customerRepository.save(c);
            return MapperToDto.CustomerToDto(customerSaved);
        }catch (Exception e ){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public CustomerDto updateCustomer(String id, AccountCustomer account) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if( !customerOptional.isPresent()){
            throw new CustomerNotFound("Customer not found with id : " + id);
        }
        Customer customerUpdate = customerOptional.get();
        customerUpdate.setName(account.getName());
        customerUpdate.setPhoneNumber(account.getPhoneNumber());
        customerUpdate.setEmail(account.getEmail());
        customerUpdate.setTimestamp(LocalDate.now());
        Customer customerSaved = customerRepository.save(customerUpdate);
        try{
            return MapperToDto.CustomerToDto(customerSaved);
        }catch (Exception e ){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteCustomer(String id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if( !customerOptional.isPresent()){
            throw new CustomerNotFound("Customer not found with id : " + id);
        }
        Customer customer = customerOptional.get();
        customer.setStatus(Status.DELETE);
        try{
            customerRepository.save(customer);
        }catch (Exception e ){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void activeCustomer(String id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if( !customerOptional.isPresent()){
            throw new CustomerNotFound("Customer not found with id : " + id);
        }
        Customer customer = customerOptional.get();
        customer.setStatus(Status.ACTIVE);
        try{
            customerRepository.save(customer);
        }catch (Exception e ){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(MapperToDto::CustomerToDto).toList();
    }

    @Override
    @Transactional
    public void updatePassword(String id,  String passwordNew) {
        Optional<Customer> customerOptional = customerRepository.getCustomerById(id);
        if( !customerOptional.isPresent()){
            throw new CustomerNotFound("Customer not found with id : " + id);
        }
        if( customerOptional.get().getAccount() == null){
            throw new ErrorException("Customer hasn't account");
        }else{
            customerOptional.get().getAccount().setPassword(bCryptPasswordEncoder.encode(passwordNew));
        }
        try{
            customerRepository.save(customerOptional.get());
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public CustomerDto getCustomerById(String id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(customerOptional.isEmpty()){
            throw new CustomerNotFound("Customer not found with id : " + id);
        }
        CustomerDto customerDto =  MapperToDto.CustomerToDto(customerOptional.get());
        return customerDto;
    }

    @Override
    public List<CustomerDto> getCustomerByCustom(String page, String limit, String status, String orderBy, String asc, String q) {
        Pageable pageable;
        List<Customer> customers;
        if( asc.equals("asc")){
            pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(limit), Sort.by(orderBy));
        }else{
            pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(limit), Sort.by(orderBy).descending());
        }

        if( status.equals("none")){
            customers = customerRepository.findAllCustomer(pageable, q).toList();
        }else {
            customers = customerRepository.findAllCustomer(pageable, q, Status.valueOf(status)).toList();
        }
        return customers.stream().map(MapperToDto::CustomerToDto).toList();
    }

    @Override
    public CustomerDto getCustomerByUsername(String username) {
        Optional<Customer> customerOptional = customerRepository.getCustomerByAccount_UserName(username);
        if( customerOptional.isEmpty() ){
            throw new CustomerNotFound("Customer not found with username : " + username);
        }
        return MapperToDto.CustomerToDto(customerOptional.get());
    }
}
