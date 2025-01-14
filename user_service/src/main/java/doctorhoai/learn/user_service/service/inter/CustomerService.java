package doctorhoai.learn.user_service.service.inter;

import doctorhoai.learn.user_service.dto.CustomerDto;
import doctorhoai.learn.user_service.dto.EmployeeDto;
import doctorhoai.learn.user_service.dto.request.CustomerRequest;
import doctorhoai.learn.user_service.dto.request.EmployeeRequest;

import java.util.List;

public interface CustomerService {
    CustomerDto addCustomer(CustomerRequest customer);
    CustomerDto updateCustomer(String id, CustomerRequest customer);
    void deleteCustomer(String id);
    void activeCustomer(String id);
    List<CustomerDto> getAllCustomers();
    void updatePassword(String id, String passwordOld, String passwordNew);
    CustomerDto getCustomerById(String id);
}
