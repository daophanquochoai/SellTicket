package doctorhoai.learn.user_service.helper;

import doctorhoai.learn.user_service.dto.AccountDto;
import doctorhoai.learn.user_service.dto.CustomerDto;
import doctorhoai.learn.user_service.dto.EmployeeDto;
import doctorhoai.learn.user_service.dto.RoleDto;
import doctorhoai.learn.user_service.entity.*;

public class MapperToDto {
    public static Role DtoToRole(RoleDto roleDto) {
        if(roleDto == null) return null;
        return Role.builder()
                .id(roleDto.getId())
                .roleName(roleDto.getRoleName())
                .status(Status.valueOf(roleDto.getStatus().toUpperCase()))
                .build();
    }
    public static RoleDto RoleToDto(Role role) {
        if(role == null) return null;
        return RoleDto.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .status(role.getStatus().toString())
                .build();
    }
    public static EmployeeDto EmployeeToDto(Employee employee){
        AccountDto accountDto = MapperToDto.AccountToDto(employee.getAccount());
        return EmployeeDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .CCCD(employee.getCCCD())
                .email(employee.getEmail())
                .account(accountDto)
                .build();
    }
    public static Employee DtoToEmployee(EmployeeDto employeeDto) {
        Account account = MapperToDto.DtoToAccount(employeeDto.getAccount());
        return Employee.builder()
                .id(employeeDto.getId())
                .name(employeeDto.getName())
                .email(employeeDto.getEmail())
                .CCCD(employeeDto.getCCCD())
                .account(account)
                .build();
    }
    public static AccountDto AccountToDto(Account account){
        if( account == null ) return null;
        RoleDto roleDto = RoleToDto(account.getRole());
        return AccountDto.builder()
                .userName(account.getUserName())
                .password(account.getPassword())
                .role(roleDto)
                .active(account.getActive().toString())
                .build();
    }
    public static Account DtoToAccount(AccountDto accountDto){
        if( accountDto == null ) return null;
        Role role = DtoToRole(accountDto.getRole());
        return Account.builder()
                .userName(accountDto.getUserName())
                .password(accountDto.getPassword())
                .role(role)
                .active(Status.valueOf(accountDto.getActive().toUpperCase()))
                .build();
    }
    public static Customer DtoToCustomer(CustomerDto customerDto){
        return Customer.builder()
                .id(customerDto.getId())
                .name(customerDto.getName())
                .phoneNumber( customerDto.getPhoneNumber())
                .email(customerDto.getEmail())
                .account(MapperToDto.DtoToAccount(customerDto.getAccount()))
                .timestamp(customerDto.getTimestamp())
                .build();
    }
    public static CustomerDto CustomerToDto(Customer customer){
        return CustomerDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phoneNumber(customer.getPhoneNumber())
                .email(customer.getEmail())
                .account(MapperToDto.AccountToDto(customer.getAccount()))
                .timestamp(customer.getTimestamp())
                .build();
    }
}
