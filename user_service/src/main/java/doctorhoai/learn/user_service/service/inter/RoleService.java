package doctorhoai.learn.user_service.service.inter;

import doctorhoai.learn.user_service.dto.RoleDto;
import java.util.List;


public interface RoleService {
    //xpa role
    void deleteRole(int id);
    // lay role theo id
    RoleDto getRole(int id);
    // lay tat ca role
    List<RoleDto> getRoles();
    // them role
    RoleDto addRole(RoleDto role);
    // cap nhat role
    RoleDto updateRole(int id, RoleDto role);
}
