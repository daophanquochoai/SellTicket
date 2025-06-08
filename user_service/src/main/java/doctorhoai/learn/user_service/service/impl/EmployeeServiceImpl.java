package doctorhoai.learn.user_service.service.impl;

import doctorhoai.learn.user_service.dto.request.EmployeeChange;
import doctorhoai.learn.user_service.dto.EmployeeDto;
import doctorhoai.learn.user_service.dto.request.EmployeeRequest;
import doctorhoai.learn.user_service.entity.Account;
import doctorhoai.learn.user_service.entity.Employee;
import doctorhoai.learn.user_service.entity.Role;
import doctorhoai.learn.user_service.entity.Status;
import doctorhoai.learn.user_service.exception.DuplicateEmployee;
import doctorhoai.learn.user_service.exception.EmployeeNotFound;
import doctorhoai.learn.user_service.exception.ErrorException;
import doctorhoai.learn.user_service.exception.RoleNotFound;
import doctorhoai.learn.user_service.helper.MapperToDto;
import doctorhoai.learn.user_service.repository.AccountRepository;
import doctorhoai.learn.user_service.repository.EmployeeRepository;
import doctorhoai.learn.user_service.repository.RoleRepository;
import doctorhoai.learn.user_service.service.inter.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CustomerServiceImpl customerServiceImpl;

    @Override
    @Transactional
    public EmployeeDto addEmployee(EmployeeRequest employee) {
        Optional<Role> role = roleRepository.findById(2);
        if( role.isEmpty()){
            throw new RoleNotFound("Role not found with id : " + employee.getRoleId());
        }
        List<Employee> employees = employeeRepository.findByEmployee(employee.getEmail(), employee.getUserName());
        if( employees.size() > 0 ){
            throw new DuplicateEmployee("Duplicate employee : " + employee.getEmail() + " or " + employee.getUserName());
        }
        try{
            Account account = Account.builder()
                    .userName(employee.getUserName())
                    .password(bCryptPasswordEncoder.encode(employee.getPassword()))
                    .active(Status.ACTIVE)
                    .role(role.get())
                    .build();
            Account accountSaved = accountRepository.save(account);
            Employee e = Employee.builder()
                    .name(employee.getName())
                    .CCCD(employee.getCCCD())
                    .email(employee.getEmail())
                    .account(accountSaved)
                    .status(Status.ACTIVE)
                    .build();
            return MapperToDto.EmployeeToDto(employeeRepository.save(e));
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public EmployeeDto updateEmployee(String id, EmployeeChange employee) {
        Optional<Employee> empOp = employeeRepository.findById(id);
        if( empOp.isEmpty()){
            throw new EmployeeNotFound("Employee not found with id : " + id);
        }
        Employee empOld = empOp.get();
        empOld.setName(employee.getName());
        empOld.setEmail(employee.getEmail());
        empOld.setCCCD(employee.getCCCD());
        if( employee.getPassword().length() > 0){
            empOld.getAccount().setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
        }
        try{
            return MapperToDto.EmployeeToDto(employeeRepository.save(empOld));
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }


    @Override
    @Transactional
    public void deleteEmployee(String id) {
        Optional<Employee> empOp = employeeRepository.findById(id);
        if( empOp.isEmpty()){
            throw new EmployeeNotFound("Employee not found with id : " + id);
        }
        Employee employee = empOp.get();
        employee.setStatus(Status.DELETE);
        try{
            employeeRepository.save(employee);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void activeEmployee(String id) {
        Optional<Employee> empOp = employeeRepository.findById(id);
        if( empOp.isEmpty()){
            throw new EmployeeNotFound("Employee not found with id : " + id);
        }
        Employee employee = empOp.get();
        employee.setStatus(Status.ACTIVE);
        try{
            employeeRepository.save(employee);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        try{
            List<Employee> employees = employeeRepository.findAll();
            return employees.stream().map(MapperToDto::EmployeeToDto).toList();
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void updatePassword(String id, String password, String newPassword) {
        Optional<Employee> empOp = employeeRepository.findById(id);
        if( empOp.isEmpty()){
            throw new EmployeeNotFound("Employee not found with id : " + id);
        }
        Employee employee = empOp.get();
        if( employee.getAccount() == null){
            throw new EmployeeNotFound("Employee hasn't account");
        }else{
            if( employee.getAccount().getPassword().equals(password)){
                employee.getAccount().setPassword(newPassword);
            }else {
                throw new ErrorException("Wrong password");
            }
        }
        try{
            employeeRepository.save(employee);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public Page<EmployeeDto> getEmployee(String limit, String page, String q, String asc, String status, String orderBy) {
        Page<Employee> employees;
        Pageable pageable;
        if( asc.equals("asc") ){
            pageable = PageRequest.of(Integer.parseInt(limit), Integer.parseInt(page), Sort.by(orderBy));
        }else{
            pageable = PageRequest.of(Integer.parseInt(limit), Integer.parseInt(page), Sort.by(orderBy).descending());
        }
        if( status.equals("none") ){
            employees = employeeRepository.getAllByCustom(pageable,q);
        }else {
            employees = employeeRepository.getAllByCustom(pageable, q, Status.valueOf(status));
        }
        return employees.map(MapperToDto::EmployeeToDto);
    }

    @Override
    public EmployeeDto getEmployeeByUsername(String username) {
        Optional<Employee> employeeOptional = employeeRepository.getEmployeeByAccount_UserName(username);
        if( employeeOptional.isEmpty() ){
            throw new EmployeeNotFound("Employee not found with username : " + username);
        }
        return MapperToDto.EmployeeToDto(employeeOptional.get());
    }

    @Override
    public EmployeeDto resetAccount(String id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if( employeeOptional.isEmpty() ){
            throw new EmployeeNotFound("Employee not found with id : " + id);
        }
        employeeOptional.get().getAccount().setPassword(bCryptPasswordEncoder.encode("123456"));
        try{
            Employee employeeSaved = employeeRepository.save(employeeOptional.get());
            return MapperToDto.EmployeeToDto(employeeSaved);
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }
}
