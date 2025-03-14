package doctorhoai.learn.user_service.service.inter;

import doctorhoai.learn.user_service.dto.CustomerDto;
import doctorhoai.learn.user_service.dto.request.AccountCustomer;
import doctorhoai.learn.user_service.dto.request.CustomerRequest;

import java.util.List;

public interface CustomerService {
    CustomerDto addCustomer(CustomerRequest customer);
    CustomerDto updateCustomer(String id, AccountCustomer account);
    void deleteCustomer(String id);
    void activeCustomer(String id);
    List<CustomerDto> getAllCustomers();
    void updatePassword(String id, String passwordOld, String passwordNew);
    CustomerDto getCustomerById(String id);
    List<CustomerDto> getCustomerByCustom(String page, String limit, String status, String orderBy, String asc, String q);
    CustomerDto getCustomerByUsername(String username);
}
