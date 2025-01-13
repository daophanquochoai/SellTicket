package doctorhoai.learn.user_service.helper;

import doctorhoai.learn.user_service.dto.RoleDto;
import doctorhoai.learn.user_service.entity.Role;
import doctorhoai.learn.user_service.entity.Status;

public class MapperToDto {
    public Role DtoToRole(RoleDto roleDto) {
        return Role.builder()
                .id(roleDto.getId())
                .roleName(roleDto.getRoleName())
                .status(Status.valueOf(roleDto.getStatus().toUpperCase()))
                .build();
    }
    public RoleDto RoleToDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .status(role.getStatus().toString())
                .build();
    }
}
