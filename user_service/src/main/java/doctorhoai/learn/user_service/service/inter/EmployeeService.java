package doctorhoai.learn.user_service.service.inter;

import doctorhoai.learn.user_service.dto.EmployeeDto;
import doctorhoai.learn.user_service.dto.request.EmployeeRequest;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EmployeeService {
    EmployeeDto addEmployee(EmployeeRequest employee);
    EmployeeDto updateEmployee(String id,EmployeeRequest employee);
    void deleteEmployee(String id);
    void activeEmployee(String id);
    List<EmployeeDto> getAllEmployees();
    void updatePassword(String id, String password, String newPassword);
}
